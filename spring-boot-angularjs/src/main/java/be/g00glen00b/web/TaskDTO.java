package be.g00glen00b.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskDTO {
    private Long id;
    @NotNull(message = "task.description.notNull")
    @Size(min = 3, max = 64, message = "task.description.size")
    private String description;
    private boolean completed;
}
