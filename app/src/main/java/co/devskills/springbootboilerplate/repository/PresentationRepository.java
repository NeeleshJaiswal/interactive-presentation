package co.devskills.springbootboilerplate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.devskills.springbootboilerplate.entity.Presentation;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, UUID> {
}