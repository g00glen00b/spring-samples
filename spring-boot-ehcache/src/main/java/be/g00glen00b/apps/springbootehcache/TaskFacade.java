package be.g00glen00b.apps.springbootehcache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskFacade {

    @Caching(
        cacheable = @Cacheable(value = "tasks", condition = "!#noCache", key = "'ALL'"),
        put = @CachePut(value = "tasks", condition = "#noCache", key = "'ALL'"))
    public List<TaskDTO> findAll(boolean noCache) {
        return List.of(
            new TaskDTO(1L, "My first task", true),
            new TaskDTO(2L, "My second task", false));
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void clearCache() {
        // not need to do anything
    }
}
