package codes.dimitri.examples.mailpit;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Testcontainers
@SpringBootTest(classes = {
    MailService.class,
    MailSenderAutoConfiguration.class,
    RestClientAutoConfiguration.class,
    MailServiceTest.Configuration.class
})
class MailServiceTest {
    @Autowired
    private MailService service;
    @Autowired
    private MailpitClient mailpitClient;

    @Container
    static GenericContainer<?> mailpitContainer = new GenericContainer<>("axllent/mailpit:v1.18")
        .withExposedPorts(1025, 8025)
        .waitingFor(Wait.forLogMessage(".*accessible via.*", 1));

    @DynamicPropertySource
    static void configureMail(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", mailpitContainer::getHost);
        registry.add("spring.mail.port", mailpitContainer::getFirstMappedPort);
        registry.add("mailpit.web.port", () -> mailpitContainer.getMappedPort(8025));
    }

    @AfterEach
    void tearDown() {
        mailpitClient.deleteAllMessages();
    }

    @Test
    void send() throws MessagingException {
        service.send();
        ObjectNode result = mailpitClient.findFirstMessage();
        assertSoftly(softly -> {
            softly.assertThat(result.get("From").get("Address").asText()).isEqualTo("noreply@example.org");
            softly.assertThat(result.get("Subject").asText()).isEqualTo("Test email");
            softly.assertThat(result.get("To").get(0).get("Address").asText()).isEqualTo("me@example.org");
            softly.assertThat(result.get("Text").asText()).isEqualTo("""
                ********************
                This is a test email
                ********************
                
                Please do not respond to this email.""");
        });
    }

    @Test
    void send_messageIsSupportedByAllClients() throws MessagingException {
        service.send();
        ObjectNode result = mailpitClient.findFirstMessage();
        ObjectNode htmlCheckResult = mailpitClient.htmlCheck(result.get("ID").asText());
        assertThat(htmlCheckResult.get("Total").get("Supported").asInt()).isEqualTo(100);
    }

    @TestConfiguration
    static class Configuration {
        @Bean
        RestClient mailpitRestClient(RestClient.Builder builder, @Value("${spring.mail.host}") String host, @Value("${mailpit.web.port}") int port) {
            return builder
                .baseUrl("http://" + host + ":" + port + "/api/v1")
                .build();
        }

        @Bean
        MailpitClient mailpitClient(RestClient mailpitRestClient) {
            return new MailpitClient(mailpitRestClient);
        }
    }
}