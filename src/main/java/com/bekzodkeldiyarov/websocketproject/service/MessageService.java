package com.bekzodkeldiyarov.websocketproject.service;

import com.bekzodkeldiyarov.websocketproject.entity.Message;
import com.bekzodkeldiyarov.websocketproject.entity.User;

import java.util.List;

public interface MessageService {
    void save(Message message);
    List<Message> getAllMessagesByToLogin(String username);
    List<Message> getMessagesByFromLoginAndToLoginOrderByIdDesc(String fromLogin, String toLogin);
    List<Message> getAllMessagesByFromLogin(String username);
}
