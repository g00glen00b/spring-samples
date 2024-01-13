package codes.dimitri.htmx.todoitem.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "todoitem")
class TodoItem {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private TodoItemOwner owner;
    private String task;
    private boolean completed;
    @CreatedDate
    private Instant createdAt;

    public void toggle() {
        this.completed = !this.completed;
    }

    public TodoItem(TodoItemOwner owner, String task) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.task = task;
        this.completed = false;
    }
}
