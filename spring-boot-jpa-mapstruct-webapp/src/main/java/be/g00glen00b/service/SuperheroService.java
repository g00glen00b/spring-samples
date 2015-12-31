package be.g00glen00b.service;

import be.g00glen00b.dto.SuperheroDTO;

import java.util.List;

public interface SuperheroService {
    List<SuperheroDTO> findAll();
}
