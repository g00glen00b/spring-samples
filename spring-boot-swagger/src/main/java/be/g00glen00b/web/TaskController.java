package be.g00glen00b.web;

import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import be.g00glen00b.dto.MessageDTO;
import be.g00glen00b.dto.TaskDTO;
import be.g00glen00b.service.TaskNotFoundException;
import be.g00glen00b.service.TaskServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@Api("/api/tasks")
public class TaskController {
    @Autowired
    private TaskServiceImpl service;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all tasks", notes = "Retrieving the collection of user tasks", response = TaskDTO[].class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = TaskDTO[].class)
    })
    public List<TaskDTO> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create task", notes = "Creating a new user task", response = TaskDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = TaskDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = MessageDTO.class)
    })
    public TaskDTO create(
            @ApiParam(required = true, name = "task", value = "New task")
            @Valid @RequestBody TaskDTO dto) {
        return service.create(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update task", notes = "Updating an existing user task", response = TaskDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = TaskDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = MessageDTO.class),
        @ApiResponse(code = 404, message = "Not found", response = MessageDTO.class)
    })
    public TaskDTO update(
            @ApiParam(required = true, name = "id", value = "ID of the task you want to update", defaultValue = "0")
            @PathVariable Long id,
            @ApiParam(required = true, name = "task", value = "Updated task")
            @Valid @RequestBody TaskDTO dto) {
        return service.update(id, dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete task", notes = "Deleting an existing user task")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Success"),
        @ApiResponse(code = 404, message = "Not found", response = MessageDTO.class)
    })
    public void delete(
            @ApiParam(required = true, name = "id", value = "ID of the task you want to delete")
            @PathVariable Long id) {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageDTO handleValidationException(MethodArgumentNotValidException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String code = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new MessageDTO(messageSource.getMessage(code, null, locale));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TaskNotFoundException.class)
    public MessageDTO handleNotFoundException(TaskNotFoundException ex) {
        String[] parameters = {Long.toString(ex.getId())};
        Locale locale = LocaleContextHolder.getLocale();
        return new MessageDTO(messageSource.getMessage("exception.TaskNotFound.description", parameters, locale));
    }
}
