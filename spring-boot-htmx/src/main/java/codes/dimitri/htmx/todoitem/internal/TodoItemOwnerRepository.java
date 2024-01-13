package codes.dimitri.htmx.todoitem.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface TodoItemOwnerRepository extends JpaRepository<TodoItemOwner, UUID> {
}
