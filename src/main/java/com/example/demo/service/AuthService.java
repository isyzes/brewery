package com.example.demo.service;

import com.example.demo.dto.authentication.UserSignInRequest;
import com.example.demo.dto.authentication.UserSignInResponse;
import com.example.demo.dto.authentication.UserSignUpRequest;
import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.SuchUserAlreadyExistException;
import com.example.demo.exception.SuchUserNotFoundException;
import com.example.demo.mapper.EmployeeSignUpRequestMapper;
import com.example.demo.repository.AuthInfoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.security.Roles.CONSUMER;
import static com.example.demo.security.Roles.EMPLOYEE;

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
    public UserSignInResponse signIn(final UserSignInRequest request) throws SuchUserNotFoundException {
        final String email = request.getEmail();
        final String password = request.getPassword();
        final Optional<AuthInfoEntity> optional = getAuthInfoByLogin(email);

        if (optional.isPresent()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            final AuthInfoEntity authInfoEntity = optional.get();
            return getToken(email, password, authInfoEntity.getRoles());
        }

        throw new SuchUserNotFoundException("User with email=" + email + " not found!");
    }

    @Transactional
    public UserSignInResponse signUp(final UserSignUpRequest request) throws SuchUserAlreadyExistException {
        final String email = request.getEmail();
        final String password = request.getPassword();

        if (getAuthInfoByLogin(email).isPresent()) {
            throw new SuchUserAlreadyExistException("User with email=" + request.getEmail() + " already exists");
        } else {
            saveUser(request);
            return getToken(email, password);
        }
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

    private String generateToken(final String email, final String password, final Collection<Roles> roles) {
        return jwtUtil.generateToken(new User(email, password, roles));
    }

    private UserSignInResponse getToken(final String email, final String password) {
        return new UserSignInResponse(generateToken(email, password, Collections.singleton(CONSUMER)));
    }

    private UserSignInResponse getToken(final String email, final String password, final Collection<Roles> roles) {
        return new UserSignInResponse(generateToken(email, password, roles));
    }

    private Optional<AuthInfoEntity> getAuthInfoByLogin(final String email) {
        return authInfoRepository.findByLogin(email);
    }

    public List<AuthInfoEntity> getStaff() {
        return authInfoRepository.findByRoles(EMPLOYEE);
    }
}
