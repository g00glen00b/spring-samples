package be.g00glen00b.service.impl;

import be.g00glen00b.dto.SuperheroDTO;
import be.g00glen00b.repository.SuperheroRepository;
import be.g00glen00b.service.SuperheroService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuperheroServiceImpl implements SuperheroService {
    @Autowired
    private SuperheroRepository repository;
    @Autowired
    private DozerBeanMapper mapper;

    @Override
    public List<SuperheroDTO> findAll() {
        return repository.findAll().stream()
            .map(entity -> mapper.map(entity, SuperheroDTO.class))
            .collect(Collectors.toList());
    }
}
