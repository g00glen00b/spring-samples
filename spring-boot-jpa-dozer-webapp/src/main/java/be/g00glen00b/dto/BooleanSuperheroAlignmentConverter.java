package be.g00glen00b.dto;

import org.dozer.DozerConverter;

public class BooleanSuperheroAlignmentConverter extends DozerConverter<Boolean, SuperheroAlignmentDTO> {

    public BooleanSuperheroAlignmentConverter() {
        super(Boolean.class, SuperheroAlignmentDTO.class);
    }

    @Override
    public SuperheroAlignmentDTO convertTo(Boolean source, SuperheroAlignmentDTO destination) {
        if (source == null) {
            return null;
        } else if (source) {
            return SuperheroAlignmentDTO.GOOD;
        } else {
            return SuperheroAlignmentDTO.EVIL;
        }
    }

    @Override
    public Boolean convertFrom(SuperheroAlignmentDTO source, Boolean destination) {
        if (source == null) {
            return null;
        } else {
            return SuperheroAlignmentDTO.GOOD.equals(source);
        }
    }
}
