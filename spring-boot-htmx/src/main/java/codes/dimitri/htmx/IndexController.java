package codes.dimitri.htmx;

import codes.dimitri.htmx.account.AccountUserDTO;
import codes.dimitri.htmx.account.AccountManagement;
import codes.dimitri.htmx.todoitem.TodoItemManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
    private final AccountManagement accounts;
    private final TodoItemManagement todoItems;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal AccountUserDTO user) {
        UUID accountId = user.id();
        model.addAttribute("account", accounts.findById(accountId));
        model.addAttribute("items", todoItems.findAllByOwnerId(accountId));
        return "index";
    }
}
