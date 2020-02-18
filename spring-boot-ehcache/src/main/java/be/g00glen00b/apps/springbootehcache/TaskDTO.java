package be.g00glen00b.apps.springbootehcache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class TaskDTO {
    private final long id;
    private final String task;
    private final boolean completed;
}
