package codes.dimitri.htmx.account;

import java.util.UUID;

public record AccountCreatedEvent(UUID id, String username) {
}
