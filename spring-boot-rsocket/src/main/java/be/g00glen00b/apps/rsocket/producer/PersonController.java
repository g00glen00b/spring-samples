package be.g00glen00b.apps.rsocket.producer;

import be.g00glen00b.apps.rsocket.PersonMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
@Profile("producer")
@RequiredArgsConstructor
public class PersonController {
    private final PersonRepository repository;

    @MessageMapping("findPeople")
    public Flux<PersonMessage> findAll() {
        return repository.findAll().map(Person::toMessage);
    }
}
