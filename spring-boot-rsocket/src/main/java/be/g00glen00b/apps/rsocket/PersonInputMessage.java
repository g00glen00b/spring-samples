package be.g00glen00b.apps.rsocket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PersonInputMessage {
    private final Integer page;
    private final Integer pageSize;
}
