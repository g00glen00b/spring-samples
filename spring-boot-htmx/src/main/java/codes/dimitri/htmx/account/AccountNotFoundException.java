package codes.dimitri.htmx.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AccountNotFoundException extends RuntimeException {
    private final UUID id;

    @Override
    public String getMessage() {
        return "Account not found";
    }
}
