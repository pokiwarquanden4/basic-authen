package com.example.basicauthen.repository;

import com.example.basicauthen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE `client_secret` = :client_secret AND `client_id` = :client_id", nativeQuery = true)
    User findByIDSecret(String client_id, String client_secret);
}
