package be.g00glen00b.web;

import java.io.IOException;
import java.util.HashMap;
import javax.validation.Valid;
import be.g00glen00b.dto.MessageDTO;
import be.g00glen00b.dto.TaskDTO;
import be.g00glen00b.service.TaskServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class TaskController {
    @Autowired
    private TaskServiceImpl service;
    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("tasks", service.findAll());
        model.addAttribute("newTask", new TaskDTO());
        return "tasks";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@RequestParam Long id, TaskDTO task) {
        service.update(id, task);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("newTask") TaskDTO task) {
        service.create(task);
        return "redirect:/";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleClientError(HttpClientErrorException ex, Model model) throws IOException {
        MessageDTO dto = mapper.readValue(ex.getResponseBodyAsByteArray(), MessageDTO.class);
        model.addAttribute("error", dto.getMessage());
        return findAll(model);
    }

}
