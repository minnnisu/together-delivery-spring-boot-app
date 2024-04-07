package org.minnnisu.togetherdelivery.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameDuplicationCheckDto {
    @NotBlank(message = "NoUsernameError")
    @Size(min = 4, max = 20, message = "TooShortOrLongUsernameError")
    /*
     * 문자열이 영문 대소문자와 숫자로만 이루어져 있다
     */
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "NotValidUsernameError")
    String username;
}
