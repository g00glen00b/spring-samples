package be.g00glen00b.controller;

import be.g00glen00b.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/superhero")
public class SuperheroController {
    @Autowired
    private SuperheroRepository repository;

    @RequestMapping
    public ModelAndView getSuperheroes() {
        return new ModelAndView("superheroes", "superheroes", repository.findAll());
    }
}
