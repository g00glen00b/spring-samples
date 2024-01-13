package codes.dimitri.htmx.account.internal;

import codes.dimitri.htmx.account.AccountDTO;
import codes.dimitri.htmx.account.AccountUserDTO;
import org.mapstruct.Mapper;

@Mapper
interface AccountMapper {
    AccountUserDTO toAccountUser(Account user);
    AccountDTO toDto(Account user);
}
