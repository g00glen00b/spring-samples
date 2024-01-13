package codes.dimitri.htmx.todoitem.internal;

import codes.dimitri.htmx.todoitem.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class TodoItemManagementImpl implements TodoItemManagement {
    private final TodoItemRepository items;
    private final TodoItemOwnerRepository owners;
    private final TodoItemMapper mapper;

    @Override
    @Transactional
    public TodoItemDTO create(CreateTodoItemRequestDTO request) {
        TodoItemOwner owner = findOwnerEntity(request);
        TodoItem item = new TodoItem(owner, request.task());
        return mapper.toDto(items.save(item));
    }

    @Override
    @Transactional
    public void delete(TodoItemIdentity request) {
        TodoItem item = findEntityByIdAndOwnerId(request.id(), request.ownerId());
        items.delete(item);
    }

    @Override
    @Transactional
    public void toggleComplete(TodoItemIdentity request) {
        TodoItem item = findEntityByIdAndOwnerId(request.id(), request.ownerId());
        item.toggle();
    }

    private TodoItem findEntityByIdAndOwnerId(UUID id, UUID ownerId) {
        return items.findByIdAndOwnerId(id, ownerId).orElseThrow(() -> new TodoItemNotFoundException(id));
    }

    @Override
    public List<TodoItemDTO> findAllByOwnerId(UUID ownerId) {
        List<TodoItem> items = this.items.findAllByOwnerIdOrderByCreatedAt(ownerId);
        return items.stream().map(mapper::toDto).toList();
    }

    private TodoItemOwner findOwnerEntity(CreateTodoItemRequestDTO request) {
        return owners.findById(request.ownerId()).orElseThrow(() -> new InvalidTodoItemException("Invalid owner", request));
    }
}
