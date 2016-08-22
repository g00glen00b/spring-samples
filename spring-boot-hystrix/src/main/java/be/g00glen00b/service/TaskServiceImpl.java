package be.g00glen00b.service;

import be.g00glen00b.dto.TaskDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TaskServiceImpl {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CacheManager cacheManager;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @CachePut("tasks")
    @HystrixCommand(fallbackMethod = "findAllFallback", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"),
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10")
    })
    public TaskDTO[] findAll() {
        logger.info("Calling microservice");
        return restTemplate.getForObject("http://task-service/api/tasks", TaskDTO[].class);
    }

    public TaskDTO[] findAllFallback() {
        if (cacheManager.getCache("tasks") != null && cacheManager.getCache("tasks").get(SimpleKey.EMPTY) != null) {
            return cacheManager.getCache("tasks").get(SimpleKey.EMPTY, TaskDTO[].class);
        } else {
            return null;
        }
    }
}
