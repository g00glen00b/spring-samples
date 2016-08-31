package be.g00glen00b.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaskDTO {
    private Long id;
    @NotNull(message = "task.description.notNull")
    @Size(min = 3, max = 64, message = "task.description.size")
    private String description;
    private boolean completed;

    public TaskDTO(Long id, String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public TaskDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
