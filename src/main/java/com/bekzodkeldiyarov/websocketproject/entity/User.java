package com.bekzodkeldiyarov.websocketproject.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String login;

    public User(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Id != null && Objects.equals(Id, user.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
