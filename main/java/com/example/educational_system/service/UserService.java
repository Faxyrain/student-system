package com.example.educational_system.service;

import com.example.educational_system.entity.User;
import com.example.educational_system.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public boolean isUsernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllStudents() {
        return userRepository.findByRole("student");
    }

    public long countByRole(String role) { // 新增方法
        return userRepository.countByRole(role);
    }

    public Optional<User> findFirstByRole(String role) { // 新增方法
        return userRepository.findFirstByRole(role);
    }

}