package codes.dimitri.htmx.todoitem.internal;

import codes.dimitri.htmx.account.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class TodoItemOwnerManagement {
    private final TodoItemOwnerRepository repository;

    @ApplicationModuleListener
    public void on(AccountCreatedEvent event) {
        repository.save(new TodoItemOwner(event.id()));
    }
}
