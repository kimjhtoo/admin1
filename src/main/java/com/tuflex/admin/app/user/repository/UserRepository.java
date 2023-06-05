package com.tuflex.admin.app.user.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tuflex.admin.app.user.model.Role;
import com.tuflex.admin.app.user.model.User;

// @Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);

        Optional<User> findBySnsTypeAndSnsId(String snsType, String snsId);

        Optional<User> findByEmailAndSnsType(String email, String snsType);

        Optional<User> findByEmailAndSnsTypeAndSnsId(String email, String snsType, String snsId);

        Boolean existsByEmail(String email);

        Boolean existsBySnsTypeAndSnsId(String snsType, String snsId);

        Boolean existsByEmailAndSnsType(String email, String snsType);

        Boolean existsByEmailAndSnsTypeAndSnsId(String email, String snsType, String snsId);

        List<User> findAllByEmailContaining(String email);

        List<User> findAllByEmailContainingAndRoles(String email, Role role);

        Long countByNameContainingAndRolesIn(String name, Collection<Role> role);

        Long countByEmailContainingAndRolesIn(String email, Collection<Role> role);

        Long countByNameContainingAndRolesInOrEmailContainingAndRolesIn(String name,
                        Collection<Role> role1, String email, Collection<Role> role2);

        Page<User> findByNameContainingAndRolesIn(Pageable pageable, String name, Collection<Role> role);

        Page<User> findByEmailContainingAndRolesIn(Pageable pageable, String email, Collection<Role> role);

        List<User> findByNameContainingAndRolesIn(String name, Collection<Role> role);

        List<User> findByEmailContainingAndRolesIn(String email, Collection<Role> role);

        Page<User> findByNameContainingAndRolesInOrEmailContainingAndRolesIn(Pageable pageable, String name,
                        Collection<Role> role1, String email, Collection<Role> role2);

}