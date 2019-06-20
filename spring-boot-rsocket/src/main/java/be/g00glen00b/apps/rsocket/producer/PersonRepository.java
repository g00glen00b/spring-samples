package be.g00glen00b.apps.rsocket.producer;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Profile("producer")
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
}
