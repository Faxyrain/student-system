package com.example.educational_system.controller;

import com.example.educational_system.entity.User;
import com.example.educational_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showRoleSelection() {
        return "role_selection";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam("role") String role, Model model) {
        model.addAttribute("role", role);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("role") String role, HttpSession session, Model model) {
        User user = userService.login(username, password).orElse(null);
        if (user != null && user.getRole().equals(role)) {
            session.setAttribute("user", user);
            if ("student".equals(role)) {
                return "redirect:/student_dashboard";
            } else if ("teacher".equals(role)) {
                return "redirect:/teacher_dashboard";
            }
        }
        model.addAttribute("role", role);
        model.addAttribute("error", "用户名或密码错误");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(@RequestParam("role") String role, Model model) {
        model.addAttribute("role", role);
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword, @RequestParam("role") String role,
            HttpSession session, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("role", role);
            model.addAttribute("error", "两次输入的密码不一致");
            return "register";
        }
        if (userService.isUsernameExists(username)) {
            model.addAttribute("role", role);
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        User registeredUser = userService.register(user);
        session.setAttribute("user", registeredUser);
        if ("student".equals(role)) {
            // User teacher = userService.findFirstByRole("teacher").orElseThrow(() -> new
            // RuntimeException("系统中没有老师账号"));
            // 若有学生与老师的关联字段（如 `User` 中有 `teacher` 字段），则进行关联设置
            // 示例：user.setTeacher(teacher);
            return "redirect:/student_dashboard";
        } else if ("teacher".equals(role)) {
            return "redirect:/teacher_dashboard";
        }
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}