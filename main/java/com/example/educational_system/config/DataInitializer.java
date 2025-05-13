package com.example.educational_system.config;

import com.example.educational_system.entity.User;
import com.example.educational_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.countByRole("teacher") == 0) { // 修正方法调用参数写法
            User teacher = new User();
            teacher.setUsername("teacher");
            teacher.setPassword("teacher123"); // 建议后续使用加密密码
            teacher.setRole("teacher");
            userRepository.save(teacher);
            System.out.println("默认老师账号已创建");
        }
    }
}