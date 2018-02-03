package be.g00glen00b.crawler.webcrawlerapi;

import be.g00glen00b.crawler.webcrawlerapi.crawler.CrawlerOptions;
import be.g00glen00b.crawler.webcrawlerapi.webpage.WebpageHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@EnableScheduling
public class WebCrawlerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebCrawlerApiApplication.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix = "crawler")
    public CrawlerOptions crawlerOptions() {
        return new CrawlerOptions();
    }

    @Bean
    public RouterFunction<?> routes(WebpageHandler handler) {
        return route(GET("/api/webpage"), handler::findAll);
    }
}
