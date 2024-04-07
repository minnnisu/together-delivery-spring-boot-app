package org.minnnisu.togetherdelivery.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameDuplicationCheckDto {
    @NotBlank(message = "NoNicknameError")
    @Size(min = 2, max = 10, message = "TooShortOrLongNicknameError")
    String nickname;
}
