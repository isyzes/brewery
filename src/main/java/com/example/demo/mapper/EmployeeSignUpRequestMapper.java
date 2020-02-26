package com.example.demo.mapper;

import com.example.demo.dto.authentication.UserSignUpRequest;
import com.example.demo.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeSignUpRequestMapper extends DestinationMapper<UserSignUpRequest, UserEntity> {
}
