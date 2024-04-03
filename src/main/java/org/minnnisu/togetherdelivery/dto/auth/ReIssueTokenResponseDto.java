package org.minnnisu.togetherdelivery.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReIssueTokenResponseDto {
    private String accessToken;

    public static ReIssueTokenResponseDto fromDto(ReIssueTokenDto reIssueTokenDto){
        return ReIssueTokenResponseDto.builder()
                .accessToken(reIssueTokenDto.getToken())
                .build();
    }
}
