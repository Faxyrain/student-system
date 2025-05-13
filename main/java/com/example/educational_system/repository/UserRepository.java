package com.example.educational_system.repository;

import com.example.educational_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    List<User> findByRole(String role);

    long countByRole(String role); // 新增方法

    @Query("SELECT u FROM User u WHERE u.role = :role")
    Optional<User> findFirstByRole(String role); // 新增方法

}