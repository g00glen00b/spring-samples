package codes.dimitri.htmx.account.internal;

import codes.dimitri.htmx.account.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class AccountManagementImpl implements AccountManagement {
    private final AccountRepository accounts;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper mapper;
    private final ApplicationEventPublisher events;


    @Override
    @Transactional
    public AccountDTO register(CreateAccountRequestDTO request) {
        validateUniqueUsername(request);
        validatePassword(request);
        String password = passwordEncoder.encode(request.password());
        Account user = new Account(request.username(), password);
        AccountDTO dto = mapper.toDto(accounts.save(user));
        events.publishEvent(new AccountCreatedEvent(dto.id(), dto.username()));
        return dto;
    }

    @Override
    public AccountDTO findById(UUID id) {
        Account account = findEntityById(id);
        return mapper.toDto(account);
    }

    private Account findEntityById(UUID id) {
        return accounts.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    private void validateUniqueUsername(CreateAccountRequestDTO request) {
        if (accounts.existsByUsername(request.username())) {
            throw new InvalidAccountException("There is already a user with this username", request);
        }
    }

    private void validatePassword(CreateAccountRequestDTO request) {
        if (!request.password().equals(request.repeatPassword())) {
            throw new InvalidAccountException("Passwords do not match", request);
        }
    }
}
