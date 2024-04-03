package org.minnnisu.togetherdelivery.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    String message;

    public LoginResponseDto(){
        this.message = "성공적으로 로그인하였습니다.";
    }
}
