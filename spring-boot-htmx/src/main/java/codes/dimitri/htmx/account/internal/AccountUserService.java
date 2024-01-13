package codes.dimitri.htmx.account.internal;

import codes.dimitri.htmx.account.AccountUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AccountUserService implements UserDetailsService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public AccountUserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository
            .findByUsername(username)
            .map(mapper::toAccountUser)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
