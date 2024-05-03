package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.comment.CommentResponseDto;
import org.minnnisu.togetherdelivery.dto.reply.*;
import org.minnnisu.togetherdelivery.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping()
    public ResponseEntity<ReplyListResponseDto> getReplyList(@RequestParam(value = "cursor", required = false) Long cursor, @RequestParam("commentId") Long commentId) {
        ReplyListResponseDto replyListResponseDto = replyService.getReplyList(cursor, commentId);
        return new ResponseEntity<>(replyListResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReplySaveResponseDto> saveReply(@AuthenticationPrincipal User user,
                                                          @Valid @RequestBody ReplySaveRequestDto replySaveRequestDto) {
     ReplySaveResponseDto replySaveResponseDto = replyService.saveReply(user, replySaveRequestDto);
     return new ResponseEntity<>(replySaveResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ReplyUpdateResponseDto> updateReply(@AuthenticationPrincipal User user,
                                                              @Valid @RequestBody ReplyUpdateRequestDto replyUpdateRequestDto) {
        ReplyUpdateResponseDto replyUpdateResponseDto = replyService.updateReply(user, replyUpdateRequestDto);
        return new ResponseEntity<>(replyUpdateResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReplyDeleteResponseDto> deleteReply(@AuthenticationPrincipal User user,
                                                                @PathVariable("id") Long replyId
    ){
        ReplyDeleteResponseDto replyDeleteResponseDto = replyService.deleteReply(user, replyId);
        return new ResponseEntity<>(replyDeleteResponseDto, HttpStatus.CREATED);
    }
}
