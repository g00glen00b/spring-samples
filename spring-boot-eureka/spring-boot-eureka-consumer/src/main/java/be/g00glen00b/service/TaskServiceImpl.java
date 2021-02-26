package be.g00glen00b.service;

import be.g00glen00b.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl {

    private final RestTemplate restTemplate;

    public static final String TASK_API = "http://task-service/api/tasks";

    public List<TaskDTO> findAll() {
        return restTemplate.exchange(TASK_API, HttpMethod.GET, null, new ListOfTaskDTO()).getBody();
    }

    private static class ListOfTaskDTO extends ParameterizedTypeReference<List<TaskDTO>> {
    }
}
