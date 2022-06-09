package com.bekzodkeldiyarov.websocketproject.service;

import com.bekzodkeldiyarov.websocketproject.entity.Message;
import com.bekzodkeldiyarov.websocketproject.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessagesByToLogin(String username) {
        return messageRepository.getMessagesByToLoginOrderByIdDesc(username);
    }

    @Override
    public List<Message> getMessagesByFromLoginAndToLoginOrderByIdDesc(String fromLogin, String toLogin) {
        return messageRepository.getMessagesByFromLoginAndToLoginOrderByIdDesc(fromLogin, toLogin);
    }

    @Override
    public List<Message> getAllMessagesByFromLogin(String username) {
        return messageRepository.getMessagesByFromLoginOrderByIdDesc(username);
    }
}
