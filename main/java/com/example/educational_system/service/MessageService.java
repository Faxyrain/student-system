package com.example.educational_system.service;

import com.example.educational_system.entity.Message;
import com.example.educational_system.entity.User;
import com.example.educational_system.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(User sender, User receiver, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSendTime(LocalDateTime.now());
        message.setReadStatus(false);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesBetweenUsers(User user1, User user2) {
        return messageRepository.findBySenderAndReceiverOrSenderAndReceiverOrderBySendTimeAsc(
                user1, user2, user2, user1);
    }

    public void markMessagesAsRead(User sender, User receiver) {
        List<Message> messages = messageRepository.findBySenderAndReceiverAndReadStatus(
                sender, receiver, false);
        messages.forEach(message -> message.setReadStatus(true));
        messageRepository.saveAll(messages);
    }
}