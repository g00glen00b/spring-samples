package codes.dimitri.htmx.todoitem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class TodoItemNotFoundException extends RuntimeException {
    private final UUID id;

    @Override
    public String getMessage() {
        return "Todo item not found";
    }
}
