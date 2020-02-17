package com.example.demo.controller;

import com.example.demo.dto.Employee;
import com.example.demo.service.EmployeeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/staff/")
public class EmployeeController {
    private final EmployeeService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "employee/take", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Employee toHire(@RequestBody Employee employee) {
        return service.toHire(employee);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "employee/to-dismiss/{idEmployee}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void toDismiss(@PathVariable long idEmployee) {
        service.toDismiss(idEmployee);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "list")
    public List<Employee> getStaffList() {
        return service.getStaff();
    }
}
