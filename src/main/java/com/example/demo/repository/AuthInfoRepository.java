package com.example.demo.repository;

import com.example.demo.entity.AuthInfoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthInfoRepository extends JpaRepository<AuthInfoEntity, Long> {
    Optional<AuthInfoEntity> findByLogin(String username);

}
