package com.example.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "AuthInfo")
public class AuthInfoEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthInfoEntity that = (AuthInfoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password.length(), that.password.length()) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, user);
    }
}
