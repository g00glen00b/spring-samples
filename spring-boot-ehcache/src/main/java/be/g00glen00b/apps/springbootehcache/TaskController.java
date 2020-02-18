package be.g00glen00b.apps.springbootehcache;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskFacade taskFacade;

    @GetMapping
    public List<TaskDTO> findAll(@RequestParam(required = false) boolean noCache) {
        return taskFacade.findAll(noCache);
    }

    @DeleteMapping("/cache")
    public void clearCache() {
        taskFacade.clearCache();
    }
}
