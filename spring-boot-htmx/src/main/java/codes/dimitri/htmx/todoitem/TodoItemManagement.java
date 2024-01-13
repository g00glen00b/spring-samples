package codes.dimitri.htmx.todoitem;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface TodoItemManagement {
    @Transactional
    TodoItemDTO create(CreateTodoItemRequestDTO request);

    @Transactional
    void delete(TodoItemIdentity request);

    @Transactional
    void toggleComplete(TodoItemIdentity request);

    List<TodoItemDTO> findAllByOwnerId(UUID ownerId);
}
