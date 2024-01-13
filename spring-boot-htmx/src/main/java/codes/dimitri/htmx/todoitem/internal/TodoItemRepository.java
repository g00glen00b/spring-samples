package codes.dimitri.htmx.todoitem.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface TodoItemRepository extends JpaRepository<TodoItem, UUID> {
    List<TodoItem> findAllByOwnerIdOrderByCreatedAt(UUID ownerId);
    Optional<TodoItem> findByIdAndOwnerId(UUID id, UUID ownerId);
}
