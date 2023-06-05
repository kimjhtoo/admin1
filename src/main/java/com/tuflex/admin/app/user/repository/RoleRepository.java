package com.tuflex.admin.app.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.model.Role;

// @Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}