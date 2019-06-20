package be.g00glen00b.apps.rsocket.consumer;

import be.g00glen00b.apps.rsocket.PersonInputMessage;
import be.g00glen00b.apps.rsocket.PersonMessage;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootApplication
public class RSocketConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RSocketConsumerApplication.class, args);
    }

    @Bean
    public ApplicationRunner consumer(RSocketStrategies strategies) {
        return args -> {
            CountDownLatch closeLatch = new CountDownLatch(1);
            rSocket()
                .doOnNext(socket -> log.info("🚀 Connected to RSocket"))
                .map(rsocket -> rSocketRequester(strategies, rsocket))
                .flatMapMany(this::findPeople)
                .map(PersonMessage::toString)
                .subscribe(log::info, err -> log.error("Something went wrong", err), closeLatch::countDown);
            closeLatch.await();
        };
    }

    private Flux<PersonMessage> findPeople(RSocketRequester requester) {
        return requester
            .route("findPeople")
            .data(DefaultPayload.create(""))
            .retrieveFlux(PersonMessage.class);
    }

    private RSocketRequester rSocketRequester(RSocketStrategies strategies, RSocket rsocket) {
        return RSocketRequester.wrap(rsocket, MimeTypeUtils.parseMimeType("application/cbor"), strategies);
    }

    private Mono<RSocket> rSocket() {
        return RSocketFactory
            .connect()
            .dataMimeType("application/cbor")
            .frameDecoder(PayloadDecoder.ZERO_COPY)
            .transport(TcpClientTransport.create(8000))
            .start();
    }
}
