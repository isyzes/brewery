package com.example.demo.controller;

import com.example.demo.dto.Employee;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/staff/")
public class EmployeeController {

    private final UserService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "employee/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Employee create(@RequestBody final Employee employee) {
        return service.create(employee);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "employee/to-dismiss/{idEmployee}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void toDismiss(@PathVariable final long idEmployee) {
        service.toDismiss(idEmployee);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "list")
    public List<Employee> getStaff() {
        return service.getStaff();
    }
}
