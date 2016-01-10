package be.g00glen00b.dto;

public class SuperheroDTO {
    private Long id;
    private String name;
    private SuperheroIdentityDTO identity;
    private SuperheroAlignmentDTO alignment;

    public SuperheroDTO(Long id, String name, SuperheroIdentityDTO identity, SuperheroAlignmentDTO alignment) {
        this.id = id;
        this.name = name;
        this.identity = identity;
        this.alignment = alignment;
    }

    public SuperheroDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public SuperheroIdentityDTO getIdentity() {
        return identity;
    }
    public void setIdentity(SuperheroIdentityDTO identity) {
        this.identity = identity;
    }

    public SuperheroAlignmentDTO getAlignment() {
        return alignment;
    }
    public void setAlignment(SuperheroAlignmentDTO alignment) {
        this.alignment = alignment;
    }
}
