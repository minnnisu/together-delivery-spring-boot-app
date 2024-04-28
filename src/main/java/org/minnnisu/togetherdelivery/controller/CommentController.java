package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.comment.CommentSaveRequestDto;
import org.minnnisu.togetherdelivery.dto.comment.CommentSaveResponseDto;
import org.minnnisu.togetherdelivery.dto.comment.CommentUpdateRequestDto;
import org.minnnisu.togetherdelivery.dto.comment.CommentUpdateResponseDto;
import org.minnnisu.togetherdelivery.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<CommentSaveResponseDto> saveComment(@AuthenticationPrincipal User user,
                                                              @Valid @RequestBody CommentSaveRequestDto commentSaveRequestDto
    ){
        CommentSaveResponseDto commentSaveResponseDto = commentService.saveComment(user, commentSaveRequestDto);
        return new ResponseEntity<>(commentSaveResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<CommentUpdateResponseDto> updateComment(@AuthenticationPrincipal User user,
                                                                  @Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto
    ){
        CommentUpdateResponseDto commentUpdateResponseDto = commentService.updateComment(user, commentUpdateRequestDto);
        return new ResponseEntity<>(commentUpdateResponseDto, HttpStatus.CREATED);
    }
}
