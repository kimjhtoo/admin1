package com.tuflex.admin.app.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tuflex.admin.app.user.model.Admin;
import com.tuflex.admin.app.user.model.Role;

// @Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
        Optional<Admin> findByPhone(String phone);

        Boolean existsByPhone(String phone);

        Optional<Admin> findByPhoneAndPassword(String phone, String password);

        // @Query(name = "select u from Admin u inner join Admin_role a inner join Role
        // r on u.pid=a.admin_pid AND a.role_id=r.id where r.name=:name", nativeQuery =
        // true)
        List<Admin> searchByRoles(@Param("name") String name);

        // @Query(name = "select * from `admin` where `phone` like
        // CONCAT('%',:phone,'%')", nativeQuery = true)
        // List<Admin> searchByPhone(@Param("phone") String phone);

        // @Query(name = "select * from `admin` where `name` like
        // CONCAT('%',:name,'%')", nativeQuery = true)
        // List<Admin> searchByName(@Param("name") String name);

        // @Query(name = "select u from Admin u where u.roles in :roles", nativeQuery =
        // true)
        List<Admin> findByRolesInAndIsEnable(Set<Role> roles, Boolean isEnable);

        List<Admin> findAllByPhoneContainingAndIsEnable(String phone, Boolean isEnable);

        List<Admin> findAllByNameContainingAndIsEnable(String name, Boolean isEnable);

        List<Admin> findAllByPhoneContainingOrNameContainingOrRolesIn(String phone, String name,
                        Set<Role> roles);

        List<Admin> findAllByNameContaining(String name);

        @Modifying
        @Transactional
        @Query(value = "update admin a set a.is_enable = 0 where a.pid = :pid", nativeQuery = true)
        public void withdrawal(@Param("pid") Long pid);

        @Modifying
        @Transactional
        @Query(value = "update admin a set a.password = :password where a.phone = :phone", nativeQuery = true)
        public void modify(@Param("password") String password, @Param("phone") String phone);
}