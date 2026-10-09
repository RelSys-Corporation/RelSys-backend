package com.domain.auth.authManager.mappers;

import com.domain.auth.authManager.dtos.LoginOutputDto;

public class LoginDtoMapper {
    public static LoginOutputDto toDto(String token) {
        return new LoginOutputDto(token);
    }
}
