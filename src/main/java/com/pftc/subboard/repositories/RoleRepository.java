package com.pftc.subboard.repositories;

import java.util.Optional;

import com.pftc.subboard.models.role.ERole;
import com.pftc.subboard.models.role.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
