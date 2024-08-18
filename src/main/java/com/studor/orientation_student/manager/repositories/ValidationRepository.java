package com.studor.orientation_student.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.Validation;
import java.util.List;


@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long>{
    List<Validation> findByCode(String code);
}
