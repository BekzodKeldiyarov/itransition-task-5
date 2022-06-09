package com.bekzodkeldiyarov.websocketproject.service;

import com.bekzodkeldiyarov.websocketproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserService {
    User findByLogin(String login);

    void save(User user);

    List<User> findAll();
}
