package org.minnnisu.togetherdelivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test/user")
    public ResponseEntity<String> testAuthenticatedUser(@AuthenticationPrincipal User users){
        return new ResponseEntity<>("로그인한 유저: " + users.getUsername(), HttpStatus.OK);
    }

    @GetMapping("/test/noUser")
    public ResponseEntity<String> testNoAuthenticatedUser(){
        return new ResponseEntity<>("로그인 하지 않은 유저", HttpStatus.OK);
    }

}
