package be.g00glen00b.apps.rsocket.producer;

import be.g00glen00b.apps.rsocket.PersonMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private Long id;
    private String firstname;
    private String lastname;

    public PersonMessage toMessage() {
        return new PersonMessage(getId(), getFirstname(), getLastname());
    }
}
