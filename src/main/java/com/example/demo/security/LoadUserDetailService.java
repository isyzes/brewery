package com.example.demo.security;

import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.repository.AuthInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoadUserDetailService implements UserDetailsService {
    private final AuthInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<AuthInfoEntity> optional = repository.findByLogin(email);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new UsernameNotFoundException("User with email: " + email + " not found");
    }
}
