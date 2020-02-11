package com.example.demo.service;

import com.example.demo.dto.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

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
}
