package com.example.demo.service;

import com.example.demo.dto.Employee;
import com.example.demo.dto.EmployeeSignUpRequest;
import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.SuchUserAlreadyExistException;
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

@Service
@AllArgsConstructor
public class EmployeeService {
    private final AuthInfoRepository authInfoRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Employee toHire(Employee employee) {
        employee.setId(1);
        return employee;
    }

    public List<Employee> getStaff() {
        return employeeList();
    }

    public void toDismiss(long idEmployee) {
    }

    private List<Employee> employeeList() {
        Employee firstEmployee = Employee.builder()
                .id(5)
                .name("Adam Gordon")
                .department("Production")
                .wages(2500)
                .isWorks(true)
                .dateStart(LocalDate.of(2018, 1,15))
                .dateEnd(null)
                .build();

        Employee secondEmployee = Employee.builder()
                .id(2)
                .name("Carla Williams")
                .department("Production")
                .wages(5070)
                .isWorks(true)
                .dateStart( LocalDate.of(2018, 1, 15))
                .dateEnd(null)
                .build();

        Employee thirdEmployee = Employee.builder()
                .id(4)
                .name("Boris Jones")
                .department("Production")
                .wages(1500)
                .isWorks(false)
                .dateStart(LocalDate.of(2018, 1, 15))
                .dateEnd(LocalDate.of(2019,10,14))
                .build();
        return List.of(firstEmployee, secondEmployee, thirdEmployee);
    }



    private EmployeeSignUpRequestMapper employeeSignUpRequestMapper;

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
