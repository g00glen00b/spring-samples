package be.g00glen00b.controller;

import be.g00glen00b.repository.SuperheroRepository;
import be.g00glen00b.service.SuperheroService;
import be.g00glen00b.service.SuperheroesUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.EOFException;

@Controller
@RequestMapping("/superhero")
public class SuperheroController {
    @Autowired
    private SuperheroService service;

    @RequestMapping
    public ModelAndView getSuperheroes() {
        return new ModelAndView("superheroes", "superheroes", service.findAll());
    }

    @ExceptionHandler({SuperheroesUnavailableException.class})
    public ModelAndView getSuperheroesUnavailable(SuperheroesUnavailableException ex) {
        return new ModelAndView("superheroes", "error", ex.getMessage());
    }
}
