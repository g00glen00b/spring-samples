package be.g00glen00b.crawler.webcrawlerapi.webpage;

import java.time.LocalDateTime;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WebpageRepository extends ReactiveCrudRepository<Webpage, String> {
    @Query("{ '$or': [{ 'content': {'$regex': ?0 } }, { 'title': { '$regex': ?0 } }] }")
    Flux<Webpage> findAllByContentLikeOrTitleLike(String search);
    @ExistsQuery("{ 'url': ?0, 'lastCrawled': { '$gt': ?1 } }")
    Mono<Boolean> existsByUrlAndLastCrawledAfter(String url, LocalDateTime lastCrawled);
}
