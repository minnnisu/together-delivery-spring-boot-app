package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.dto.auth.*;
import org.minnnisu.togetherdelivery.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(
            @Valid @RequestBody SignupRequestDto signupRequestDto
    ) {
        SignupDto signupDto = authService.signup(signupRequestDto);
        // 회원가입 후 로그인 페이지로 이동
        return new ResponseEntity<>(SignupResponseDto.fromDto(signupDto), HttpStatus.CREATED);
    }

    @PostMapping("/check/username")
    public ResponseEntity<UsernameDuplicationCheckResponseDto> checkUsernameDuplication(
            @Valid @RequestBody UsernameDuplicationCheckDto usernameDuplicationCheckDto
    ){
        authService.checkUsernameDuplication(usernameDuplicationCheckDto);
        return new ResponseEntity<>(new UsernameDuplicationCheckResponseDto(), HttpStatus.OK);
    }

    @PostMapping("/check/nickname")
    public ResponseEntity<NicknameDuplicationCheckResponseDto> checkNicknameDuplication(
            @Valid @RequestBody NicknameDuplicationCheckDto nicknameDuplicationCheckDto
    ){
        authService.checkNicknameDuplication(nicknameDuplicationCheckDto);
        return new ResponseEntity<>(new NicknameDuplicationCheckResponseDto(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(
            @RequestHeader("Authorization") String accessToken,
            @RequestHeader("Authorization-refresh") String refreshToken
    ) {
        authService.logout(accessToken, refreshToken);

        return new ResponseEntity<>(new LogoutResponseDto(), HttpStatus.CREATED);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ReIssueTokenResponseDto> reIssueToken(
            @RequestHeader("Authorization") String accessToken,
            @RequestHeader("Authorization-refresh") String refreshToken
    ){

        ReIssueTokenDto reIssueTokenDto = authService.reIssueToken(accessToken, refreshToken);
        return new ResponseEntity<>(ReIssueTokenResponseDto.fromDto(reIssueTokenDto), HttpStatus.CREATED);
    }

}
