package org.minnnisu.togetherdelivery.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "NoUsernameError")
    @Size(min = 4, max = 20, message = "TooShortOrLongUsernameError")
    /*
     * 문자열이 영문 대소문자와 숫자로만 이루어져 있다
     */
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "NotValidUsernameError")
    private String username;


    @NotBlank(message = "NoPasswordError")
    @Size(min = 8, max = 20, message = "TooShortOrLongPasswordError")
    /*
     * 적어도 하나의 영문자를 포함해야 합니다.
     * 적어도 하나의 숫자를 포함해야 합니다.
     * 적어도 하나의 특수문자(@, $, !, %, *, #, ?, &)를 포함해야 합니다.
     * 영문 대소문자, 숫자, 특수문자 중에서만 구성되어야 합니다.
     */
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", message = "NotValidPasswordError")
    private String password;

    @NotBlank(message = "NoPasswordCheckError")
    @Size(min = 8, max = 20, message = "TooShortOrLongPasswordCheckError")
    /*
     * password 필드와 동일
     */
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", message = "NotValidPasswordError")
    private String passwordCheck;

    @NotBlank(message = "NoNameError")
    @Size(min = 2, max = 20, message = "TooShortOrLongNameError")
    private String name;

    @NotBlank(message = "NoNicknameError")
    @Size(min = 2, max = 10, message = "TooShortOrLongNicknameError")
    private String nickname;

    @Email(message = "NotValidEmailError")
    @NotBlank(message = "NoEmailError")
    @Size(max = 30, message = "TooShortOrLongEmailError")
    private String email;

    @NotBlank(message = "NoCollegeError")
    @Size(max = 20, message = "TooShortOrLongCollegeError")
    private String college;
}
