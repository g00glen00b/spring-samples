package codes.dimitri.examples.mailpit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringBootMailpitApplication {

    @Bean
    GenericContainer<?> mailpitContainer(DynamicPropertyRegistry properties) {
        var container = new GenericContainer<>("axllent/mailpit:v1.15")
            .withExposedPorts(1025, 8025)
            .waitingFor(Wait.forLogMessage(".*accessible via.*", 1));
        properties.add("spring.mail.host", container::getHost);
        properties.add("spring.mail.port", container::getFirstMappedPort);
        properties.add("mailpit.web.port", () -> container.getMappedPort(8025));
        return container;
    }

    @Bean
    public ApplicationRunner logMailpitWebPort(@Value("${spring.mail.host}") String host, @Value("${mailpit.web.port}") int port) {
        Logger log = LoggerFactory.getLogger(getClass());
        return args -> log.info("Mailpit accessible through http://{}:{}", host, port);
    }

    public static void main(String[] args) {
        SpringApplication
            .from(SpringBootMailpitApplication::main)
            .with(TestSpringBootMailpitApplication.class)
            .run(args);
    }
}
