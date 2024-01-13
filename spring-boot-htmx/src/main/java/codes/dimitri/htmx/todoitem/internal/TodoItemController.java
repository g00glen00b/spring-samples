package codes.dimitri.htmx.todoitem.internal;

import codes.dimitri.htmx.account.AccountUserDTO;
import codes.dimitri.htmx.todoitem.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/todoitem")
@RequiredArgsConstructor
public class TodoItemController {
    private final TodoItemManagement items;

    @GetMapping
    public String findItems(Model model, @AuthenticationPrincipal AccountUserDTO user) {
        UUID ownerId = user.id();
        addItemsModelAttributes(model, ownerId);
        return "fragments/items";
    }

    @PostMapping("/add")
    public String createItem(Model model, @RequestParam("new-task") String newTask, @AuthenticationPrincipal AccountUserDTO user) {
        UUID ownerId = user.id();
        items.create(new CreateTodoItemRequestDTO(ownerId, newTask));
        addItemsModelAttributes(model, ownerId);
        return "fragments/items";
    }

    @DeleteMapping("/{id}")
    public String deleteItem(Model model, @PathVariable UUID id, @AuthenticationPrincipal AccountUserDTO user) {
        UUID ownerId = user.id();
        items.delete(new TodoItemIdentity(ownerId, id));
        addItemsModelAttributes(model, ownerId);
        return "fragments/items";
    }

    @PutMapping("/{id}/toggle")
    public String toggleItem(Model model, @PathVariable UUID id, @AuthenticationPrincipal AccountUserDTO user) {
        UUID ownerId = user.id();
        items.toggleComplete(new TodoItemIdentity(ownerId, id));
        addItemsModelAttributes(model, ownerId);
        return "fragments/items";
    }

    @ExceptionHandler({InvalidTodoItemException.class, TodoItemNotFoundException.class})
    public ModelAndView handleError(Throwable ex, HttpServletResponse response) {
        response.setHeader("HX-Reswap", "none");
        return new ModelAndView("fragments/error", Map.of(
            "error", ex.getMessage()
        ));
    }

    private void addItemsModelAttributes(Model model, UUID ownerId) {
        model.addAttribute("items", items.findAllByOwnerId(ownerId));
    }
}
