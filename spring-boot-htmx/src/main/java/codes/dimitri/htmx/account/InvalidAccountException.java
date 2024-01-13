package codes.dimitri.htmx.account;

import lombok.Getter;

@Getter
public class InvalidAccountException extends RuntimeException {
    private final CreateAccountRequestDTO request;

    public InvalidAccountException(String message, CreateAccountRequestDTO request) {
        super(message);
        this.request = request;
    }
}
