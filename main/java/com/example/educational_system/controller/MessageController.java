package com.example.educational_system.controller;

import com.example.educational_system.entity.Message;
import com.example.educational_system.entity.User;
import com.example.educational_system.service.MessageService;
import com.example.educational_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/teacher/{studentId}")
    public String teacherChat(@PathVariable Long studentId, Model model, HttpSession session) {
        User teacher = (User) session.getAttribute("user");
        if (teacher == null || !"teacher".equals(teacher.getRole())) {
            return "redirect:/login?role=teacher";
        }

        Optional<User> studentOptional = userService.findById(studentId);
        if (studentOptional.isEmpty()) {
            return "redirect:/teacher_dashboard";
        }

        User student = studentOptional.get();
        List<Message> messages = messageService.getMessagesBetweenUsers(teacher, student);
        messageService.markMessagesAsRead(student, teacher);

        model.addAttribute("currentUser", teacher);
        model.addAttribute("otherUser", student);
        model.addAttribute("messages", messages);
        return "teacher_chat";
    }

    @GetMapping("/student/{teacherId}")
    public String studentChat(@PathVariable Long teacherId, Model model, HttpSession session) {
        User student = (User) session.getAttribute("user");
        if (student == null || !"student".equals(student.getRole())) {
            return "redirect:/login?role=student";
        }

        Optional<User> teacherOptional = userService.findById(teacherId);
        if (teacherOptional.isEmpty()) {
            return "redirect:/student_dashboard";
        }

        User teacher = teacherOptional.get();
        List<Message> messages = messageService.getMessagesBetweenUsers(student, teacher);
        messageService.markMessagesAsRead(teacher, student);

        model.addAttribute("currentUser", student);
        model.addAttribute("otherUser", teacher);
        model.addAttribute("messages", messages);
        return "student_chat";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam("senderId") Long senderId,
            @RequestParam("receiverId") Long receiverId,
            @RequestParam("content") String content,
            HttpSession session) {
        User sender = (User) session.getAttribute("user");
        if (sender == null || !sender.getId().equals(senderId)) {
            return "redirect:/login";
        }

        userService.findById(receiverId).ifPresent(receiver -> {
            messageService.sendMessage(sender, receiver, content);
        });

        if ("teacher".equals(sender.getRole())) {
            return "redirect:/messages/teacher/" + receiverId;
        } else {
            return "redirect:/messages/student/" + receiverId;
        }
    }
}