package be.g00glen00b.dto;

public class TaskDTO {
    private Long id;
    private String task;
    private boolean completed;

    public TaskDTO() {
    }

    public TaskDTO(String task, boolean completed) {
        this(null, task, completed);
    }

    public TaskDTO(Long id, String task, boolean completed) {
        this.id = id;
        this.task = task;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
