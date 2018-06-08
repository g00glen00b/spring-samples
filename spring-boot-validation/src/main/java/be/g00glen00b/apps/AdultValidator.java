package be.g00glen00b.apps;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {
	private static final int ADULT_AGE = 18;

	@Override
	public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
		return dateOfBirth != null && LocalDate.now().minusYears(ADULT_AGE).isAfter(dateOfBirth);
	}
}
