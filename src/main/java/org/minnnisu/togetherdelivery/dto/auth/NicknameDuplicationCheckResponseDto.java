package org.minnnisu.togetherdelivery.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameDuplicationCheckResponseDto {
    String message;

    public NicknameDuplicationCheckResponseDto(){
        this.message = "사용가능한 닉네임입니다.";
    }
}
