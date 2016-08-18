package be.g00glen00b.entity;

import be.g00glen00b.dto.SuperheroDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(uses = { SuperheroAlignmentMapper.class }, componentModel = "spring")
public interface SuperheroMapper {

    @Mappings({
        @Mapping(source = "good", target = "alignment"),
        @Mapping(target = "identity", expression = "java(new be.g00glen00b.dto.SuperheroIdentityDTO(superhero.getFirstName(), superhero.getLastName()))")
    })
    SuperheroDTO toSuperheroDTO(Superhero superhero);

    List<SuperheroDTO> toSuperheroDTOs(List<Superhero> superheroes);
}
