package be.g00glen00b.web;

import be.g00glen00b.service.TaskNotFoundException;
import be.g00glen00b.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskServiceImpl service;
    private final MessageSource messageSource;

    @GetMapping()
    public List<TaskDTO> findAll() {
        return service.findAll();
    }

    @PostMapping()
    public TaskDTO save(@Valid @RequestBody TaskDTO input) {
        return service.save(input);
    }

    @PutMapping(value = "/{id}")
    public TaskDTO update(@PathVariable Long id, @Valid @RequestBody TaskDTO input) {
        return service.update(id, input);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageDTO handleNotFound() {
        return new MessageDTO(messageSource.getMessage("task.notFound", null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDTO handleValidationException(MethodArgumentNotValidException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String code = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new MessageDTO(messageSource.getMessage(code, null, locale));
    }
}
