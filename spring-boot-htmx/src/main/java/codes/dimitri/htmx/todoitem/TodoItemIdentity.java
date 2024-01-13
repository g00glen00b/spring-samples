package codes.dimitri.htmx.todoitem;

import java.util.UUID;

public record TodoItemIdentity(UUID ownerId, UUID id) {
}
