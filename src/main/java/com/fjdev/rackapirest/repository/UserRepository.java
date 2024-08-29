package com.fjdev.rackapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fjdev.rackapirest.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNameC(String nameC);
}