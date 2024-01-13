package codes.dimitri.htmx.todoitem;

import java.util.UUID;

public record TodoItemDTO(UUID id, String task, boolean completed) {
}
