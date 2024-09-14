package com.studor.orientation_student.manager.repositories.establishmentsuggestcontroller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.establishmentsuggestion.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    Domain findByNom(String nom);
}
