package be.g00glen00b.apps.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {
    @NotNull(message = "Last name should not be empty")
    @Size(min = 1, max = 60, message = "Last name should be between 1 and 60 characters")
    private String lastName;
    @Size(max = 60, message = "Middle name should be at most 60 characters")
    private String middleName;
    @NotNull(message = "First name should not be empty")
    @Size(min = 1, max = 60, message = "First name should be between 1 and 60 characters")
    private String firstName;
    @NotNull(message = "Date of birth should not be empty")
    @Past(message = "Date of birth should be in the past")
    private LocalDate dateOfBirth;
    @NotNull(message = "The amount of siblings should not be empty")
    @PositiveOrZero(message = "The amount of siblings should be positive")
    private Integer siblings;
}
