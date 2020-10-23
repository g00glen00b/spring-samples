package be.g00glen00b.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String description;
    @Column
    private boolean completed;

    public Task(String description, boolean completed) {
        this.description = description;
        this.completed = completed;
    }

}
