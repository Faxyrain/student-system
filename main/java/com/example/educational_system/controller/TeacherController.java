package com.example.educational_system.controller;

import com.example.educational_system.entity.SystemMessage;
import com.example.educational_system.entity.User;
import com.example.educational_system.service.GradeService;
import com.example.educational_system.service.SystemMessageService;
import com.example.educational_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TeacherController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemMessageService systemMessageService; // 新增注入

    @GetMapping("/teacher_dashboard")
    public String showTeacherDashboard(HttpSession session, Model model) {
        User teacher = (User) session.getAttribute("user");
        if (teacher == null || !"teacher".equals(teacher.getRole())) {
            return "redirect:/";
        }
        var allGrades = gradeService.getAllGrades();
        model.addAttribute("allGrades", allGrades);
        List<User> allStudents = userService.getAllStudents();
        model.addAttribute("allStudents", allStudents);
        List<SystemMessage> unreadMessages = systemMessageService.getUnreadMessages(teacher);
        model.addAttribute("unreadMessages", unreadMessages);
        return "teacher_dashboard";
    }

    @PostMapping("/teacher/alert")
    public String sendAlert(@RequestParam("studentId") Long studentId, @RequestParam("alertMsg") String alertMsg,
            Model model) {
        // 此处可添加预警保存到数据库等逻辑
        model.addAttribute("success", "预警已发送");
        return "redirect:/teacher_dashboard";
    }
}