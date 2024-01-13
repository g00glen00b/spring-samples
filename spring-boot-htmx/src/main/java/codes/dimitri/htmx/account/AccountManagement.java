package codes.dimitri.htmx.account;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface AccountManagement {
    @Transactional
    AccountDTO register(CreateAccountRequestDTO request);

    AccountDTO findById(UUID id);
}
