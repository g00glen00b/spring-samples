package codes.dimitri.htmx.account.internal;

import codes.dimitri.htmx.account.CreateAccountRequestDTO;
import codes.dimitri.htmx.account.InvalidAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping
@RequiredArgsConstructor
class AccountController {
    private final AccountManagementImpl accounts;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("account", new CreateAccountRequestDTO(null, null, null));
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(Model model, @ModelAttribute CreateAccountRequestDTO user) {
        accounts.register(user);
        model.addAttribute("success", true);
        model.addAttribute("account", user);
        return "signup";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAccountException.class)
    public ModelAndView signupError(InvalidAccountException ex) {
        return new ModelAndView("signup", Map.of(
            "account", ex.getRequest(),
            "error", ex.getMessage()
        ));
    }
}
