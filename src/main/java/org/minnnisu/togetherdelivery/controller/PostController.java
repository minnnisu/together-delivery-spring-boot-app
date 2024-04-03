package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.post.PostSaveResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.minnnisu.togetherdelivery.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostSaveResponseDto> savePost(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PostSaveRequestDto postSaveRequestDto
    ) {
        log.info("POST /api/post");
        PostSaveResponseDto responseDto = postService.savePost(user, postSaveRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
