package be.g00glen00b.repository;

import java.util.Optional;
import be.g00glen00b.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
