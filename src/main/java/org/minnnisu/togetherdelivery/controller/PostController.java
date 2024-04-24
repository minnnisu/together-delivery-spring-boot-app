package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.post.PostListResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostDetailResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.minnnisu.togetherdelivery.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostListResponseDto> getPost(@RequestParam(required = false, defaultValue = "1") int page){
        PostListResponseDto responseDto = postService.getPost(page);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponseDto> getPost(@PathVariable Long id) {
        PostDetailResponseDto responseDto = postService.getPostDetail(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostSaveResponseDto> savePost(
            @AuthenticationPrincipal User user,
            @Valid @RequestPart PostSaveRequestDto post,
            @RequestPart(required = false) List<MultipartFile> files
    ) {
        PostSaveResponseDto responseDto = postService.savePost(user, post, files);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
