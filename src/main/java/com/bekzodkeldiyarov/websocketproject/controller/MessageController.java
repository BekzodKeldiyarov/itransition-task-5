package com.bekzodkeldiyarov.websocketproject.controller;

import com.bekzodkeldiyarov.websocketproject.entity.Message;
import com.bekzodkeldiyarov.websocketproject.entity.User;
import com.bekzodkeldiyarov.websocketproject.service.MessageService;
import com.bekzodkeldiyarov.websocketproject.service.UserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    private final UserService userService;
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(UserService userService, MessageService messageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.userService = userService;
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping({"/chat/{to}",})
    public void sendMessage(@DestinationVariable String to, Message message) {
        User userTo = userService.findByLogin(to);
        if (userTo != null) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
            message.setToLogin(userTo.getLogin());
            messageService.save(message);
        }
    }

    @GetMapping("/getMessages/{username}")
    public List<Message> getAllMessagesByToLogin(@PathVariable String username) {
        return messageService.getAllMessagesByToLogin(username);
    }

    @GetMapping("/getMessagesSendFrom/{fromLogin}/to/{toLogin}")
    public List<Message> getAllMessagesSendToLogin(@PathVariable String fromLogin, @PathVariable String toLogin) {
        return messageService.getMessagesByFromLoginAndToLoginOrderByIdDesc(fromLogin, toLogin);
    }

    @GetMapping("/getMessagesSendFrom/{fromLogin}")
    public List<Message> getAllMessagesSendFromLogin(@PathVariable String fromLogin) {
        return messageService.getAllMessagesByFromLogin(fromLogin);
    }
}
