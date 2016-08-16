package be.g00glen00b.web;

import java.util.List;
import be.g00glen00b.dto.TaskDTO;
import be.g00glen00b.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskServiceImpl service;

    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDTO> findAll(@RequestParam(required = false) boolean noCache) {
        return service.findAll(noCache);
    }

    @RequestMapping(value = "/cache", method = RequestMethod.DELETE)
    public void clearCache() {
        service.clearCache();
    }
}
