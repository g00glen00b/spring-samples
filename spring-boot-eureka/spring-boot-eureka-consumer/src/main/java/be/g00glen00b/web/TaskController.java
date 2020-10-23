package be.g00glen00b.web;

import be.g00glen00b.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl service;

    @GetMapping()
    public ModelAndView findAll() {
        return new ModelAndView("tasks", "tasks", service.findAll());
    }
}
