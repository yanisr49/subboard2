package com.pftc.subboard.repositories;

import java.util.Optional;

import com.pftc.subboard.models.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
