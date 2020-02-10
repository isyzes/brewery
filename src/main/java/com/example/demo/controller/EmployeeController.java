package com.example.demo.controller;

import com.example.demo.dto.Employee;
import com.example.demo.service.EmployeeService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@Data
@RestController
@RequestMapping(value = "/staff/")
public class EmployeeController {
    private final EmployeeService service;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "employee/take", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Employee toHire(@RequestBody Employee employee) {
        return service.toHire(employee);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "employee/to-dismiss/{idEmployee}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void toDismiss(@PathVariable long idEmployee) {
        service.toDismiss(idEmployee);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<Employee> getStaffList() {
        return service.getStaff();
    }
}
