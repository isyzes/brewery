package com.example.demo.repository;

import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.security.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthInfoRepository extends JpaRepository<AuthInfoEntity, Long> {
    Optional<AuthInfoEntity> findByLogin(String username);
    List<AuthInfoEntity> findByRoles(Roles roles);

}
