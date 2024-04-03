package org.minnnisu.togetherdelivery.dto.auth;

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