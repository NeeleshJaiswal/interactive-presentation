package co.devskills.springbootboilerplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.devskills.springbootboilerplate.entity.Presentation;

import java.util.Optional;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, String> {

    Optional<Presentation> findById(String id);  // Ensure consistency

    boolean existsById(String id);  // Helps with validation
}
