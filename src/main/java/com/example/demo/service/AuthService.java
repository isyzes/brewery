package com.example.demo.service;

import com.example.demo.dto.authentication.UserSignInRequest;
import com.example.demo.dto.authentication.UserSignInResponse;
import com.example.demo.dto.authentication.UserSignUpRequest;
import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.SuchUserAlreadyExistException;
import com.example.demo.mapper.EmployeeSignUpRequestMapper;
import com.example.demo.repository.AuthInfoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.example.demo.security.Roles.CONSUMER;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthInfoRepository authInfoRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeSignUpRequestMapper employeeSignUpRequestMapper;

    @Transactional
    public UserSignInResponse signIn(final UserSignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        final Optional<AuthInfoEntity> optional = authInfoRepository.findByLogin(request.getEmail());
        final AuthInfoEntity userEntity = optional.orElse(null);

        assert userEntity != null;
        return new UserSignInResponse(jwtUtil.generateToken(new User(request.getEmail(), request.getPassword(), userEntity.getRoles())));
    }

    @Transactional
    public UserSignInResponse signUp(final UserSignUpRequest request) throws SuchUserAlreadyExistException {
        if (authInfoRepository.findByLogin(request.getEmail()).isPresent()) {
            throw new SuchUserAlreadyExistException("User with email=" + request.getEmail() + " already exists");
        }
        saveUser(request);

        return new UserSignInResponse(jwtUtil.generateToken(new User(request.getEmail(), request.getPassword(), Collections.singleton(CONSUMER))));
    }

    private void saveUser(final UserSignUpRequest request) {
        final UserEntity userEntity = employeeSignUpRequestMapper.sourceToDestination(request);
        final UserEntity savedUser = userRepository.save(userEntity);
        saveAuthInfo(request, savedUser);
    }

    private void saveAuthInfo(final UserSignUpRequest request, final UserEntity savedUser) {
        final AuthInfoEntity authInfoEntity = new AuthInfoEntity();
        authInfoEntity.setLogin(request.getEmail());
        authInfoEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        authInfoEntity.setUser(savedUser);
        authInfoEntity.setRoles(Collections.singleton(CONSUMER));
        authInfoRepository.save(authInfoEntity);
    }
}
