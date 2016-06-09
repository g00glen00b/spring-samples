package be.g00glen00b.service;

public class TaskNotFoundException extends RuntimeException {
    private Long id;

    public TaskNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return "Task with ID '" + id + "' not found";
    }
}
