package be.g00glen00b.service;

import java.util.List;
import java.util.stream.Collectors;
import be.g00glen00b.data.Task;
import be.g00glen00b.data.TaskRepository;
import be.g00glen00b.web.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl {
    @Autowired
    private TaskRepository repository;

    public List<TaskDTO> findAll() {
        return repository.findAll().stream().map(this::getDTO).collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO save(TaskDTO input) {
        return getDTO(repository.save(new Task(input.getDescription(), input.isCompleted())));
    }

    @Transactional
    public TaskDTO update(Long id, TaskDTO input) {
        Task task = repository.findOneOptional(id).orElseThrow(TaskNotFoundException::new);
        task.setDescription(input.getDescription());
        task.setCompleted(input.isCompleted());
        return getDTO(task);
    }

    @Transactional
    public void delete(Long id) {
        Task task = repository.findOneOptional(id).orElseThrow(TaskNotFoundException::new);
        repository.delete(task);
    }

    private TaskDTO getDTO(Task entity) {
        return new TaskDTO(entity.getId(), entity.getDescription(), entity.isCompleted());
    }
}
