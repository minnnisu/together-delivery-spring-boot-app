package org.minnnisu.togetherdelivery.dto.auth;

import lombok.*;
import org.minnnisu.togetherdelivery.domain.User;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SignupDto {
    private String username;

    public static SignupDto fromEntity(User user){
        return SignupDto.builder()
                .username(user.getUsername())
                .build();
    }
}
