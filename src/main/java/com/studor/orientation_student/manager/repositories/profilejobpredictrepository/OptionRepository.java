package com.studor.orientation_student.manager.repositories.profilejobpredictrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.profilejobprediction.Subdomain;
import java.util.List;
import com.studor.orientation_student.entities.establishmentsuggestion.Domain;

@Repository
public interface OptionRepository extends JpaRepository<Subdomain, Long> {
    Subdomain findByNom(String nom);

    List<Subdomain> findByDomain(Domain domain);
}
