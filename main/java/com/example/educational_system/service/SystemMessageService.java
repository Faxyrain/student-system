package com.example.educational_system.service;

import com.example.educational_system.entity.SystemMessage;
import com.example.educational_system.entity.User;
import com.example.educational_system.repository.SystemMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemMessageService {
    @Autowired
    private SystemMessageRepository systemMessageRepository;

    public SystemMessage sendMessage(User recipient, String content) {
        SystemMessage message = new SystemMessage();
        message.setRecipient(recipient);
        message.setContent(content);
        message.setSendTime(LocalDateTime.now());
        message.setRead(false);
        return systemMessageRepository.save(message);
    }

    public List<SystemMessage> getUnreadMessages(User recipient) {
        return systemMessageRepository.findByRecipientAndReadOrderBySendTimeDesc(recipient, false);
    }

    public void markAsRead(Long messageId) {
        systemMessageRepository.findById(messageId).ifPresent(message -> {
            message.setRead(true);
            systemMessageRepository.save(message);
        });
    }
}