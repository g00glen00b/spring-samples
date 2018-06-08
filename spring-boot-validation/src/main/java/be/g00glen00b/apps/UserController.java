package be.g00glen00b.apps;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
	private UserRepository repository;

	@PostMapping
	@Transactional
	public User save(@RequestBody @Valid UserInput user) {
		return repository.saveAndFlush(new User(
			null,
			user.getLastName(),
			user.getMiddleName(),
			user.getFirstName(),
			user.getDateOfBirth(),
			user.getSiblings()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ex.getBindingResult()
			.getAllErrors().stream()
			.map(ObjectError::getDefaultMessage)
			.collect(Collectors.toList());
	}
}
