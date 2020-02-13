package com.example.demo.mapper;

import com.example.demo.dto.EmployeeSignUpRequest;
import com.example.demo.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeSignUpRequestMapper {
    UserEntity sourceToDestination(EmployeeSignUpRequest source);

    EmployeeSignUpRequest destinationToSource(UserEntity destination);
}
