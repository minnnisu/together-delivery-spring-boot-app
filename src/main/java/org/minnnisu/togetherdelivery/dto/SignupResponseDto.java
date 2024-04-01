package org.minnnisu.togetherdelivery.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SignupResponseDto {
    private String username;

    public static SignupResponseDto fromDto(SignupDto signupDto){
        return SignupResponseDto.builder()
                .username(signupDto.getUsername())
                .build();
    }
}