package be.g00glen00b.apps.user;

import be.g00glen00b.apps.api.ApiError;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserRepository repository;

    @PostMapping
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = User[].class),
        @ApiResponse(code = 400, message = "Bad request", response = ApiError[].class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError[].class)
    })
    public User save(@RequestBody @Valid UserInput user) {
        return repository.saveAndFlush(new User(
            null,
            user.getLastName(),
            user.getMiddleName(),
            user.getFirstName(),
            user.getDateOfBirth(),
            user.getSiblings()));
    }

    @GetMapping
    @Validated
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = User[].class, responseHeaders = {
            @ResponseHeader(name = "X-Users-Total", description = "Total number of users that can be found", response = Long.class),
        }),
        @ApiResponse(code = 400, message = "Bad request", response = ApiError[].class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError[].class)
    })
    public ResponseEntity<List<User>> findAll(
        @ApiParam("Page number of the results you want to fetch")
        @Valid @Positive @RequestParam(required = false, defaultValue = "1") int page,
        @ApiParam("Total amount of elements on a specific page")
        @Valid @Positive @RequestParam(required = false, defaultValue = "10") int size) {
        HttpHeaders headers = new HttpHeaders();
        Page<User> users = repository.findAll(PageRequest.of(page, size));
        headers.add("X-Users-Total", Long.toString(users.getTotalElements()));
        return new ResponseEntity<>(users.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = User[].class),
        @ApiResponse(code = 400, message = "Bad request", response = ApiError[].class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError[].class)
    })
    public User findOne(@ApiParam("The unique identifier of the user you prefer to fetch") @PathVariable Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with id '" + id + "' is not found"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
            .getAllErrors().stream()
            .map(err -> new ApiError(err.getCodes(), err.getDefaultMessage()))
            .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ApiError> handleValidationExceptions(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
            .stream()
            .map(err -> new ApiError(err.getPropertyPath().toString(), err.getMessage()))
            .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public List<ApiError> handleNotFoundExceptions(UserNotFoundException ex) {
        return Collections.singletonList(new ApiError("user.notfound", ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public List<ApiError> handleOtherException(Exception ex) {
        return Collections.singletonList(new ApiError(ex.getClass().getCanonicalName(), ex.getMessage()));
    }
}
