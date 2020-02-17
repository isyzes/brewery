package com.example.demo.mapper;

import com.example.demo.dto.EmployeeSignUpRequest;
import com.example.demo.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeSignUpRequestMapper extends DestinationMapper<EmployeeSignUpRequest, UserEntity> {
}
