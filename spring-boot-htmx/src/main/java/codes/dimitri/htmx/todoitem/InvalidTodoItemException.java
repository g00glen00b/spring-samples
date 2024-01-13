package codes.dimitri.htmx.todoitem;

public class InvalidTodoItemException extends RuntimeException {
    private final CreateTodoItemRequestDTO request;

    public InvalidTodoItemException(String message, CreateTodoItemRequestDTO request) {
        super(message);
        this.request = request;
    }
}
