package be.g00glen00b.apps.rsocket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class PersonMessage {
    private final Long id;
    private final String firstname;
    private final String lastname;
}
