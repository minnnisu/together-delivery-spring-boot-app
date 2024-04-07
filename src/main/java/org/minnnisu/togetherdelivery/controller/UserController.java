package org.minnnisu.togetherdelivery.controller;

import lombok.RequiredArgsConstructor;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.user.UserInfoResponseDto;
import org.minnnisu.togetherdelivery.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    final UserService userService;

    @GetMapping()
    public ResponseEntity<UserInfoResponseDto> getUserInfo(
            @AuthenticationPrincipal User user
    ) {
        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo(user);
        return new ResponseEntity<UserInfoResponseDto>(userInfoResponseDto, HttpStatus.OK);
    }
}
