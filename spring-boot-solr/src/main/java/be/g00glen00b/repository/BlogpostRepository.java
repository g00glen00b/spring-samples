package be.g00glen00b.repository;

import java.util.Optional;
import be.g00glen00b.entities.Blogpost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogpostRepository extends JpaRepository<Blogpost, Long> {
    @Query("SELECT p FROM Blogpost p LEFT JOIN FETCH p.tags")
    Page<Blogpost> findAllDetailed(Pageable page);

    @Query("SELECT p FROM Blogpost p LEFT JOIN FETCH p.tags WHERE p.id = ?1")
    Optional<Blogpost> findOneDetailed(Long id);
}
