package be.g00glen00b.entity;

import be.g00glen00b.dto.SuperheroAlignmentDTO;
import org.springframework.stereotype.Component;

@Component
public class SuperheroAlignmentMapper {

    public SuperheroAlignmentDTO toAlignment( boolean good) {
        if (good) {
            return SuperheroAlignmentDTO.GOOD;
        } else {
            return SuperheroAlignmentDTO.EVIL;
        }
    }
}
