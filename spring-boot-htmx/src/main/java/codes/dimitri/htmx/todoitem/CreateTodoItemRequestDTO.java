package codes.dimitri.htmx.todoitem;

import java.util.UUID;

public record CreateTodoItemRequestDTO(UUID ownerId, String task) {
}
