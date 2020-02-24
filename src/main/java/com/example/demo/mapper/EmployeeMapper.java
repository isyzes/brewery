package com.example.demo.mapper;

import com.example.demo.dto.Employee;
import com.example.demo.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends DestinationMapper<Employee, UserEntity> {
}
