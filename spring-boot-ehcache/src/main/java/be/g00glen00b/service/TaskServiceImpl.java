package be.g00glen00b.service;

import java.util.Arrays;
import java.util.List;
import be.g00glen00b.dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @CachePut(value = "tasks", condition = "#noCache", key = "#noCache")
    @Cacheable(value = "tasks", condition = "!#noCache", key = "!#noCache")
    public List<TaskDTO> findAll(boolean noCache) {
        logger.info("Retrieving tasks");
        return Arrays.asList(new TaskDTO(1L, "My first task", true), new TaskDTO(2L, "My second task", false));
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void clearCache() {
        logger.info("Cache cleared");
    }
}
