package org.minnnisu.togetherdelivery.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.User;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserInfoResponseDto {
    private String username;
    private String name;
    private String nickname;
    private String email;
    private String telephone;
    private String college;

    public static UserInfoResponseDto fromEntity(User user) {
        return UserInfoResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .college(user.getCollege())
                .build();
    }
}
