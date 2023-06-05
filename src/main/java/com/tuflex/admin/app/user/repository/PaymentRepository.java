package com.tuflex.admin.app.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.model.Payment;
import com.tuflex.admin.app.user.model.Role;

// @Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value = "select count(*) from payment where year(reg_dt) = :year and month(reg_dt) = :month", nativeQuery = true)
    int countByYearMonth(@Param("year") int year, @Param("month") int month);
}