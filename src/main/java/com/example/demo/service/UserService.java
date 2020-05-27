package com.example.demo.service;

import com.example.demo.dto.Employee;
import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.security.Roles.EMPLOYEE;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmployeeMapper employeeMapper;
    private final AuthService authService;

    public Employee create(final Employee employee) {
        final UserEntity employeeEntity = employeeMapper.sourceToDestination(employee);
        employeeEntity.setWorks(true);
        employeeEntity.setDateStart(LocalDate.now());
        userRepository.save(employeeEntity);
        return employee;
    }

    public List<Employee> getStaff() {
        final List<AuthInfoEntity> staff = authService.getStaff();

        return staff.stream().map(
                employee -> Employee.builder()
                        .id(employee.getUser().getId())
                        .fio(employee.getUser().getFio())
                        .wages(employee.getUser().getWages())
                        .isWorks(employee.getUser().isWorks())
                        .department(employee.getUser().getDepartment())
                        .dateStart(employee.getUser().getDateStart())
                        .dateEnd(employee.getUser().getDateEnd())
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
}