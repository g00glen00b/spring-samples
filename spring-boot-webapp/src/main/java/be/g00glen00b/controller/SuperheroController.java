package be.g00glen00b.controller;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.g00glen00b.model.Superhero;

@Controller
@RequestMapping("/superhero")
public class SuperheroController {

  @RequestMapping
  public ModelAndView getSuperheroes() {
    return new ModelAndView("superheroes", "superheroes", Arrays.asList(
       new Superhero("Clark", "Kent", "Superman", true),
       new Superhero("Siobhan", "McDougal", "Silver Banshee", false)
    ));
  }
}
