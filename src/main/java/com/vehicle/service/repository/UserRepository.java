package com.vehicle.service.repository;

import com.vehicle.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    long countByRole(String role);

    List<User> findByRoleAndNameContainingIgnoreCase(
            String role,
            String name
    );
}