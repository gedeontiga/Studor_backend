package com.studor.orientation_student.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.Hobbies;
import java.util.Optional;


@Repository
public interface HobbiesRepository extends JpaRepository <Hobbies, Long> {
    Optional<Hobbies> findByName(String name);
}
