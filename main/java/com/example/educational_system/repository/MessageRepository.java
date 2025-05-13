package com.example.educational_system.repository;

import com.example.educational_system.entity.Message;
import com.example.educational_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrSenderAndReceiverOrderBySendTimeAsc(User sender1, User receiver1,
            User sender2, User receiver2);

    List<Message> findBySenderAndReceiverAndReadStatus(User sender, User receiver, boolean readStatus);
}