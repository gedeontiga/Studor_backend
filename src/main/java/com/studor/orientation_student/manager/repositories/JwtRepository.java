package com.studor.orientation_student.manager.repositories;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.Jwt;
import com.studor.orientation_student.entities.User;


@Repository
public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Optional<Jwt> findByToken(String token);
    Optional<Jwt> findByUserAndDesactivatedIsFalseAndExpiredIsFalse(User user);
    Stream<Jwt> findByUser(User user);
    void deleteAllByDesactivatedIsTrueAndExpiredIsTrue();
}
