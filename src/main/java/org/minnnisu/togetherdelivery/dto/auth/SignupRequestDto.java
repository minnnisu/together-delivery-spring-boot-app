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
    @Size(min = 4, max = 20, message = "NotValidUsernameError")
    /*
     * 문자열이 영문 대소문자와 숫자로만 이루어져 있다
     */
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "NotValidUsernameError")
    private String username;


    @NotBlank(message = "NoPasswordError")
    @Size(min = 8, max = 20, message = "NotValidPasswordError")
    /*
     * 적어도 하나의 영문자를 포함해야 합니다.
     * 적어도 하나의 숫자를 포함해야 합니다.
     * 적어도 하나의 특수문자(@, $, !, %, *, #, ?, &)를 포함해야 합니다.
     * 영문 대소문자, 숫자, 특수문자 중에서만 구성되어야 합니다.
     */
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", message = "NotValidPasswordError")
    private String password;

    @NotBlank(message = "NoPasswordCheckError")
    @Size(min = 8, max = 20, message = "NotValidPasswordCheckError")
    /*
     * password 필드와 동일
     */
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", message = "NotValidPasswordError")
    private String passwordCheck;

    @Size(min = 2, max = 20, message = "NotValidNameError")
    @NotBlank(message = "NoNameError")
    private String name;

    @Size(min = 2, max = 10, message = "NotValidNicknameError")
    @NotBlank(message = "NoNicknameError")
    private String nickname;

    @Email
    @Size(max = 30, message = "NotValidEmailError")
    @NotBlank(message = "NoEmailError")
    private String email;

    @Size(max = 20, message = "NotValidTelephoneError")
    @NotBlank(message = "NoTelephoneError")
    private String telephone;

    @Size(max = 20, message = "NotValidCollegeError")
    @NotBlank(message = "NoCollegeError")
    private String college;
}
