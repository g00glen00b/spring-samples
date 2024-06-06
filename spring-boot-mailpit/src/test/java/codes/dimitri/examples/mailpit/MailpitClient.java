package codes.dimitri.examples.mailpit;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

public class MailpitClient {
    private final RestClient restClient;

    public MailpitClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ObjectNode findFirstMessage() {
        ObjectNode listNode = listAllMessages();
        assertThat(listNode).isNotNull();
        var id = listNode.get("messages").get(0).get("ID").asText();
        ObjectNode messageNode = findMessage(id);
        assertThat(messageNode).isNotNull();
        return messageNode;
    }

    public ObjectNode listAllMessages() {
        return restClient
            .get()
            .uri(builder -> builder.pathSegment("messages").build())
            .retrieve()
            .body(ObjectNode.class);
    }

    public ObjectNode findMessage(String messageId) {
        return restClient
            .get()
            .uri(builder -> builder
                .pathSegment("message", messageId)
                .build())
            .retrieve()
            .body(ObjectNode.class);
    }

    public void deleteAllMessages() {
        restClient
            .delete()
            .uri(builder -> builder.pathSegment("messages").build())
            .retrieve()
            .toBodilessEntity();
    }
}
