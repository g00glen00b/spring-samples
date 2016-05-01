package be.g00glen00b.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import be.g00glen00b.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TaskServiceImpl {
    @Value("${resource.tasks}")
    private String resource;
    @Value("${resource.tasks}/{id}")
    private String idResource;
    @Autowired
    private RestTemplate restTemplate;

    public List<TaskDTO> findAll() {
        return Arrays.stream(restTemplate.getForObject(resource, TaskDTO[].class)).collect(Collectors.toList());
    }

    public TaskDTO update(Long id, TaskDTO task) {
        return restTemplate.exchange(idResource, HttpMethod.PUT, new HttpEntity<>(task), TaskDTO.class, id).getBody();
    }

    public void delete(Long id) {
        restTemplate.delete(idResource, id);
    }

    public TaskDTO create(TaskDTO task) {
        return restTemplate.postForObject(resource, task, TaskDTO.class);
    }
}
