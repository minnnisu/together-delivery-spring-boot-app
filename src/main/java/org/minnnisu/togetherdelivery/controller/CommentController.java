package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.comment.*;
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

    @GetMapping()
    public ResponseEntity<CommentResponseDto> getCommentList(@RequestParam(value = "cursor", required = false) Long cursor, @RequestParam("postId") Long postId) {
        CommentResponseDto commentResponseDto = commentService.getCommentList(cursor, postId);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentDeleteResponseDto> deleteComment(@AuthenticationPrincipal User user,
                                                                  @PathVariable("id") Long commentId
    ){
        CommentDeleteResponseDto commentDeleteResponseDto = commentService.deleteComment(user, commentId);
        return new ResponseEntity<>(commentDeleteResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/creators/{id}")
    public ResponseEntity<CommentCreatorResponseDto> getCommentCreators(@PathVariable("id") Long postId) {
        CommentCreatorResponseDto commentCreatorResponseDto = commentService.getCommentCreators(postId);
        return new ResponseEntity<>(commentCreatorResponseDto, HttpStatus.OK);
    }

}
