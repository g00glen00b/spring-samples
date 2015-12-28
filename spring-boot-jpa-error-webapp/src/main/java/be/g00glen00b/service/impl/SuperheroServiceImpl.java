package be.g00glen00b.service.impl;

import be.g00glen00b.entity.Superhero;
import be.g00glen00b.repository.SuperheroRepository;
import be.g00glen00b.service.SuperheroService;
import be.g00glen00b.service.SuperheroesUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroServiceImpl implements SuperheroService {
    @Autowired
    private SuperheroRepository repository;

    @Override
    public List<Superhero> findAll() {
        if (Math.random() < 0.8) {
            return repository.findAll();
        } else {
            throw new SuperheroesUnavailableException("Superheroes are unavailable because it has 20% chance to fail");
        }
    }
}
