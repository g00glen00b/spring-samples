package be.g00glen00b.service;

import be.g00glen00b.domain.TaskRepository;
import be.g00glen00b.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl {

    private final TaskRepository repository;

    public List<TaskDTO> findAll() {
        log.info("Task service is being called");
        return repository.findAll().stream()
                .map(entity -> new TaskDTO(entity.getId(), entity.getTask(), entity.isCompleted()))
                .collect(Collectors.toList());
    }
}
