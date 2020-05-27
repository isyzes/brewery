package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import com.example.demo.security.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    UserEntity findAllByEmail(String email);
}
