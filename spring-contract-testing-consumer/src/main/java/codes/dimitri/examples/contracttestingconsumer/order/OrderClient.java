package codes.dimitri.examples.contracttestingconsumer.order;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.Optional;

public class OrderClient {

    private final RestClient restClient;

    public OrderClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Order> findAll() {
        return restClient.get()
            .uri("/orders")
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});
    }

    public Optional<Order> findById(Long id) {
        try {
            return Optional.ofNullable(
                restClient.get()
                    .uri("/orders/{id}", id)
                    .retrieve()
                    .body(Order.class)
            );
        } catch (RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();
            }
            throw e;
        }
    }
}
