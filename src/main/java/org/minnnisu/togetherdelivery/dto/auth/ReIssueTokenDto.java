package org.minnnisu.togetherdelivery.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReIssueTokenDto {
    private String token;

    public static ReIssueTokenDto of(String token){
        return ReIssueTokenDto.builder()
                .token(token)
                .build();
    }
}
