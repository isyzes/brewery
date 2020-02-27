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
import com.example.demo.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthInfoRepository authInfoRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private EmployeeSignUpRequestMapper employeeSignUpRequestMapper;


    public UserSignInResponse singIn(final UserSignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserEntity userEntity = userRepository.findAllByEmail(request.getEmail());
        return new UserSignInResponse(jwtUtil.generateToken(new User(request.getEmail(), request.getPassword(), List.of(new SimpleGrantedAuthority(userEntity.getUserRole().name())))));
    }

    @Transactional
    public UserSignInResponse signUp(final UserSignUpRequest request) throws SuchUserAlreadyExistException {
        if (authInfoRepository.findByLogin(request.getEmail()).isPresent()) {
            throw new SuchUserAlreadyExistException("User with email=" + request.getEmail() + " already exists");
        }
        saveUser(request);

        return new UserSignInResponse(jwtUtil.generateToken(new User(request.getEmail(), request.getPassword(), List.of(new SimpleGrantedAuthority("EMPLOYEE")))));
    }

    private void saveUser(final UserSignUpRequest request) {
        final UserEntity userEntity = employeeSignUpRequestMapper.sourceToDestination(request);
        userEntity.setUserRole(Roles.CONSUMER);
        final UserEntity savedUser = userRepository.save(userEntity);
        saveAuthInfo(request, savedUser);
    }

    private void saveAuthInfo(final UserSignUpRequest request, final UserEntity savedUser) {
        final AuthInfoEntity authInfoEntity = new AuthInfoEntity();
        authInfoEntity.setLogin(request.getEmail());
        authInfoEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        authInfoEntity.setUser(savedUser);
        authInfoRepository.save(authInfoEntity);
    }

}
