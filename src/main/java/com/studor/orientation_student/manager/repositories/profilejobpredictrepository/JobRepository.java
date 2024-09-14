package com.studor.orientation_student.manager.repositories.profilejobpredictrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.profilejobprediction.Job;
import com.studor.orientation_student.entities.profilejobprediction.Subdomain;

import java.util.List;
import java.sql.Blob;
import com.studor.orientation_student.entities.establishmentsuggestion.Training;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Job findByNomIgnoreCase(String nom);

    List<Job> findBySalaire(Long salaire);

    Job findByImage(Blob image);

    List<Job> findByOption(Subdomain option);

    Job findByTraining(Training training);
}
