package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Reply;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.comment.CommentDeleteResponseDto;
import org.minnnisu.togetherdelivery.dto.comment.CommentUpdateResponseDto;
import org.minnnisu.togetherdelivery.dto.reply.*;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.CommentRepository;
import org.minnnisu.togetherdelivery.repository.ReplyRepository;
import org.minnnisu.togetherdelivery.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    public ReplySaveResponseDto saveReply(User user, ReplySaveRequestDto replySaveRequestDto) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Comment comment = commentRepository.findById(replySaveRequestDto.getCommentId()).
                orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchCommentError));

        Reply reply = replyRepository.save(Reply.of(comment, user, replySaveRequestDto.getContent()));
        return ReplySaveResponseDto.fromEntity(reply);
    }

    public ReplyUpdateResponseDto updateReply(User user, ReplyUpdateRequestDto replyUpdateRequestDto) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Reply reply = replyRepository.findById(replyUpdateRequestDto.getReplyId()).
                orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchReplyError));

        if(reply.getDeletedAt() != null) {
            throw new CustomErrorException(ErrorCode.DeletedReplyError);
        }

        if (!reply.getUser().getUsername().equals(user.getUsername())) throw new CustomErrorException(ErrorCode.NotTheAuthorOfTheReply);

        reply.update(replyUpdateRequestDto.getContent());

        return ReplyUpdateResponseDto.fromEntity(reply);
    }

    public ReplyDeleteResponseDto deleteReply(User user, Long replyId) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Reply reply = replyRepository.findById(replyId).
                orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchReplyError));

        if(reply.getDeletedAt() != null) {
            throw new CustomErrorException(ErrorCode.DeletedReplyError);
        }

        if (!reply.getUser().getUsername().equals(user.getUsername())) throw new CustomErrorException(ErrorCode.NotTheAuthorOfTheReply);

        reply.delete();

        return ReplyDeleteResponseDto.fromEntity(reply);
    }
}
