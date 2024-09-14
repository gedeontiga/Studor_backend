package com.studor.orientation_student.manager.repositories;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.Jwt;
import com.studor.orientation_student.entities.User;
// import com.studor.orientation_student.entities.RefreshToken;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Optional<Jwt> findByToken(String token);

    Optional<Jwt> findByUserAndDesactivatedIsFalseAndExpiredIsFalse(User user);

    @Query("FROM Jwt j WHERE j.refreshToken.value = :value")
    Optional<Jwt> findByRefreshTokenValue(String value);

    Stream<Jwt> findByUser(User user);

    void deleteAllByDesactivatedIsTrueAndExpiredIsTrue();
}
