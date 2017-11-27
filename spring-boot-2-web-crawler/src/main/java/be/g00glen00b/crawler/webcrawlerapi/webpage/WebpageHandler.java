package be.g00glen00b.crawler.webcrawlerapi.webpage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class WebpageHandler {
    private static final String SEARCH_ATTRIBUTE = "search";
    private WebpageRepository repository;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        String parameter = request.queryParam(SEARCH_ATTRIBUTE).orElse(".*");
        return ServerResponse.ok().body(repository.findAllByContentLikeOrTitleLike(parameter), Webpage.class);
    }
}
