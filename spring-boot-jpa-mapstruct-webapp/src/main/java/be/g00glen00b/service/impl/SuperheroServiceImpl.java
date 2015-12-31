package be.g00glen00b.service.impl;

import be.g00glen00b.dto.SuperheroDTO;
import be.g00glen00b.entity.SuperheroMapper;
import be.g00glen00b.repository.SuperheroRepository;
import be.g00glen00b.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroServiceImpl implements SuperheroService {
    @Autowired
    private SuperheroRepository repository;
    @Autowired
    private SuperheroMapper mapper;

    @Override
    public List<SuperheroDTO> findAll() {
        return mapper.toSuperheroDTOs(repository.findAll());
    }
}
