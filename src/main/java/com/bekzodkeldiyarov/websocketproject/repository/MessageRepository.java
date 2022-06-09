package com.bekzodkeldiyarov.websocketproject.repository;

import com.bekzodkeldiyarov.websocketproject.entity.Message;
import com.bekzodkeldiyarov.websocketproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getMessagesByToLoginOrderByIdDesc(String username);
    List<Message> getMessagesByFromLoginAndToLoginOrderByIdDesc(String fromLogin, String toLogin);
    List<Message> getMessagesByFromLoginOrderByIdDesc(String fromLogin);
}
