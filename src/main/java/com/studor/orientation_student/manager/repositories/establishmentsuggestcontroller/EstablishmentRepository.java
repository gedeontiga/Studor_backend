package com.studor.orientation_student.manager.repositories.establishmentsuggestcontroller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.establishmentsuggestion.Establishment;

import java.util.List;
import java.sql.Blob;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
    List<Establishment> findByLocalisation(String localisation);

    Establishment findByNom(String nom);

    Establishment findByImage(Blob image);

    Establishment findByNomDirecteur(String nomDirecteur);
}
