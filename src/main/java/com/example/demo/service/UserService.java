package com.example.demo.service;

import com.example.demo.dto.Employee;
import com.example.demo.dto.EmployeeSignUpRequest;
import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.SuchUserAlreadyExistException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.mapper.EmployeeSignUpRequestMapper;
import com.example.demo.repository.AuthInfoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final AuthInfoRepository authInfoRepository;
    private final UserRepository userRepository;

    private final EmployeeMapper employeeMapper;

    private final PasswordEncoder passwordEncoder;
    private EmployeeSignUpRequestMapper employeeSignUpRequestMapper;


    public Employee created(final Employee employee) {
        final UserEntity employeeEntity = employeeMapper.sourceToDestination(employee);
        employeeEntity.setWorks(true);
        employeeEntity.setDateStart(LocalDate.now());
        userRepository.save(employeeEntity);
        return employee;
    }

    public List<Employee> getStaff() {
        return userRepository.findAllByUserRole(Roles.EMPLOYEE).stream().map(
                employeeEntity -> Employee.builder()
                        .id(employeeEntity.getId())
                        .fio(employeeEntity.getFio())
                        .wages(employeeEntity.getWages())
                        .isWorks(employeeEntity.isWorks())
                        .department(employeeEntity.getDepartment())
                        .dateStart(employeeEntity.getDateStart())
                        .dateEnd(employeeEntity.getDateEnd())
                        .build())
                .collect(Collectors.toList());
    }

    public void toDismiss(final long idEmployee) {
        final Optional<UserEntity> optionalEmployeeEntity = userRepository.findById(idEmployee);

        if (optionalEmployeeEntity.isPresent()) {
            final UserEntity employeeEntity = optionalEmployeeEntity.get();
            employeeEntity.setWorks(false);
            employeeEntity.setDateEnd(LocalDate.now());
            userRepository.save(employeeEntity);
        }
    }



    @Transactional
    public void signUp(final EmployeeSignUpRequest request) throws SuchUserAlreadyExistException {
        if (authInfoRepository.findByLogin(request.getEmail()).isPresent()) {
            throw new SuchUserAlreadyExistException("User with email=" + request.getEmail() + " already exists");
        }
        saveUser(request);
    }

    private void saveUser(final EmployeeSignUpRequest request) {
        final UserEntity userEntity = employeeSignUpRequestMapper.sourceToDestination(request);
        userEntity.setUserRole(Roles.MANAGER);
        final UserEntity savedUser = userRepository.save(userEntity);
        saveAuthInfo(request, savedUser);
    }

    private void saveAuthInfo(final EmployeeSignUpRequest request, final UserEntity savedUser) {
        final AuthInfoEntity authInfoEntity = new AuthInfoEntity();
        authInfoEntity.setLogin(request.getEmail());
        authInfoEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        authInfoEntity.setUser(savedUser);
        authInfoRepository.save(authInfoEntity);
    }
}
