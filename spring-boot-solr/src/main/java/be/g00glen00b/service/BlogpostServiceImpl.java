package be.g00glen00b.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import be.g00glen00b.dto.BlogpostDTO;
import be.g00glen00b.dto.BlogpostsDTO;
import be.g00glen00b.entities.Blogpost;
import be.g00glen00b.entities.Tag;
import be.g00glen00b.repository.BlogpostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogpostServiceImpl {
    @Autowired
    private BlogpostRepository repository;
    @Autowired
    private TagServiceImpl tagService;

    public BlogpostsDTO findAll(int offset, int limit) {
        Page<Blogpost> result = repository.findAllDetailed(new OffsetPageRequest(offset, limit, new Sort("date")));
        return new BlogpostsDTO(offset, limit, result.getTotalElements(), getDTOs(result.getContent()));
    }

    public BlogpostDTO findOne(Long id) {
        return getDTO(repository.findOneDetailed(id).orElseThrow(BlogpostNotFoundException::new));
    }

    @Transactional
    public BlogpostDTO create(BlogpostDTO dto) {
        List<Tag> tags = dto.getTags().stream()
            .map(tag -> tagService.findOrCreate(tag))
            .collect(Collectors.toList());
        Blogpost entity = new Blogpost(dto.getTitle(), dto.getContent(), tags, LocalDate.now());
        return getDTO(repository.save(entity));
    }

    public BlogpostDTO update(Long id, BlogpostDTO dto) {
        Blogpost entity = repository.findOneDetailed(id).orElseThrow(BlogpostNotFoundException::new);
        List<Tag> newTags = dto.getTags().stream()
            .map(tag -> tagService.findOrCreate(tag))
            .collect(Collectors.toList());

    }

    private BlogpostDTO getDTO(Blogpost entity) {
        List<String> tags = entity.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        return new BlogpostDTO(entity.getId(), entity.getTitle(), entity.getTitle(), tags, entity.getDate());
    }

    private List<BlogpostDTO> getDTOs(List<Blogpost> entities) {
        return entities.stream().map(this::getDTO).collect(Collectors.toList());
    }

}
