package com.example.demo.service;

import com.example.demo.dto.Employee;
import lombok.extern.java.Log;
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
        Employee firstEmployee = new Employee(5, "Adam Gordon", "Production", 2500, true, LocalDate.of(2018, 1,15), null);
        Employee secondEmployee = new Employee(2, "Carla Williams", "Production", 5070, true, LocalDate.of(2018, 1, 15), null);
        Employee thirdEmployee = new Employee(4, "Boris Jones", "Production", 1500, false, LocalDate.of(2018, 1, 15), LocalDate.of(2019,10,14));
        return List.of(firstEmployee, secondEmployee, thirdEmployee);
    }
}
