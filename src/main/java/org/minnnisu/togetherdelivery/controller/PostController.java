package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.MealCategoryCode;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.post.PostListResponseDto;
import org.minnnisu.togetherdelivery.dto.post.postDetailResponseDto.PostDetailResponseDto;
import org.minnnisu.togetherdelivery.dto.post.postSaveResponseDto.PostSaveResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.minnnisu.togetherdelivery.dto.post.postStatusToggle.PostStatusToggleDto;
import org.minnnisu.togetherdelivery.dto.post.postStatusToggle.PostStatusToggleResponseDto;
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
    public ResponseEntity<PostListResponseDto> getPost(@RequestParam(required = false) Long cursor,
                                                       @RequestParam(required = false) Boolean status,
                                                       @RequestParam(required = false) MealCategoryCode category,
                                                       @AuthenticationPrincipal User user) {
        PostListResponseDto responseDto = postService.getPosts(user, cursor, status, category);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponseDto> getPost(@PathVariable(name = "id") Long postId, @AuthenticationPrincipal User user) {
        PostDetailResponseDto responseDto = postService.getPostDetail(postId, user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostSaveResponseDto> savePost(
            @AuthenticationPrincipal User user,
            @Valid @RequestPart(name = "post") PostSaveRequestDto post,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) {
        PostSaveResponseDto responseDto = postService.savePost(user, post, files);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<PostStatusToggleResponseDto> togglePost(@AuthenticationPrincipal User user, @PathVariable Long id) {
        PostStatusToggleDto dto = postService.togglePost(user, id);
        return new ResponseEntity<>(PostStatusToggleResponseDto.fromDto(dto), HttpStatus.OK);
    }
}
