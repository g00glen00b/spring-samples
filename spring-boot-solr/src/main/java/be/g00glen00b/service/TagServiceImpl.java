package be.g00glen00b.service;

import be.g00glen00b.entities.Tag;
import be.g00glen00b.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl {
    @Autowired
    private TagRepository repository;

    public Tag findOrCreate(String name) {
        return repository.findByName(name).orElse(repository.save(new Tag(name)));
    }
}
