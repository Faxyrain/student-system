package com.example.educational_system.repository;

import com.example.educational_system.entity.SystemMessage;
import com.example.educational_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemMessageRepository extends JpaRepository<SystemMessage, Long> {
    List<SystemMessage> findByRecipientAndReadOrderBySendTimeDesc(User recipient, boolean read);
}