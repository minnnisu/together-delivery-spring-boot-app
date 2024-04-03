package org.minnnisu.togetherdelivery.dto.auth;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String username;
    private String password;
}