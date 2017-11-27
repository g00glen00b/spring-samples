package be.g00glen00b.crawler.webcrawlerapi.crawler;

import java.io.IOException;
import java.time.LocalDateTime;
import be.g00glen00b.crawler.webcrawlerapi.webpage.Webpage;
import be.g00glen00b.crawler.webcrawlerapi.webpage.WebpageRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class Crawler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private WebpageRepository repository;
    private CrawlerOptions crawlerOptions;

    @Scheduled(fixedDelayString = "${crawler.schedule-delay-millis}", initialDelay = 0)
    public void crawl() {
        repository.saveAll(crawl(Flux.just(new CrawlerCommand(crawlerOptions.getSeed(), 1)), crawlerOptions.getDepth())
            .map(result -> new Webpage(result.getUrl(), result.getTitle(), result.getContent(), LocalDateTime.now()))
            .distinct(Webpage::getUrl))
            .subscribe(webpage -> logger.info("Webpage stored {} ({})", webpage.getTitle(), webpage.getUrl()));
    }

    /**
     * Initial web crawler command.
     * @param commands
     * @param maxDepth
     * @return
     */
    public Flux<CrawlerResult> crawl(Flux<CrawlerCommand> commands, int maxDepth) {
        return commands
            .filter(command -> command.getDepth() <= maxDepth)
            .filter(command -> command.getSeed().startsWith(crawlerOptions.getSeed())) // Making sure that I'm not crawling other sites
            .distinct(CrawlerCommand::getSeed)
            .flatMap(this::getResult)
            .flatMap(result -> crawl(result, maxDepth));
    }

    /**
     * Follow-up web crawler command.
     * @param result
     * @param maxDepth
     * @return
     */
    public Flux<CrawlerResult> crawl(CrawlerResult result, int maxDepth) {
        return Mono.just(result).mergeWith(crawl(getNextCommands(result), maxDepth));
    }

    private Flux<CrawlerCommand> getNextCommands(CrawlerResult result) {
        return Flux.fromIterable(result.getHyperlinks()).map(link -> new CrawlerCommand(link, result.getDepth() + 1));
    }

    private Mono<CrawlerResult> getResult(CrawlerCommand command) {
        return Mono.just(command)
            .map(CrawlerCommand::getSeed)
            .flatMap(this::getDocument)
            .flatMap(document -> getCrawlerResult(document, command.getDepth()));
    }

    private Mono<CrawlerResult> getCrawlerResult(Document document, int depth) {
        return Flux.fromIterable(document.getElementsByTag("a"))
            .map(element -> element.absUrl("href"))
            .collectList()
            .map(hyperlinks -> new CrawlerResult(document.location(), document.title(), document.text(), hyperlinks, depth));
    }

    private Mono<Document> getDocument(String seed) {
        try {
            return Mono.just(Jsoup.connect(seed).validateTLSCertificates(false).get());
        } catch (IOException | IllegalArgumentException e) {
            logger.debug("Could not fetch from seed {}", seed, e);
            return Mono.empty();
        }
    }
}
