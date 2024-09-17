package com.studor.orientation_student.manager.repositories;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.Validation;
import com.studor.orientation_student.entities.User;


@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
    Optional<Validation> findByCode(String code);
    void deleteByExpirationInstantIsLessThanEqual(Instant instant);
    Optional<Validation> findByUserAndCode(User user, String code);
}
