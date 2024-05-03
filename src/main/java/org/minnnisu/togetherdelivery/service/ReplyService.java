package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.Reply;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.comment.*;
import org.minnnisu.togetherdelivery.dto.reply.*;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.CommentRepository;
import org.minnnisu.togetherdelivery.repository.ReplyRepository;
import org.minnnisu.togetherdelivery.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    private final int REPLY_PAGE_SIZE = 5;

    public ReplyListResponseDto getReplyList(Long replyId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchCommentError));

        /*
        replyId가 존재할 경우
        replyId에 해당하는 createdAt 추출
        replyId, commentId, createdAt(오름차순)을 기반으로 현재 커서 이후의 REPLY_PAGE_SIZE 만큼의 데이터 응답한다.

        commentId = :commentId AND ((replyId > :replyId AND createdAt = :createdAt) OR createdAt > :createdAt)
        */

        if(replyId != null) {
            Reply reply = replyRepository.findById(replyId).
                    orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchReplyError));

            Pageable replyPageable = PageRequest.of(0, REPLY_PAGE_SIZE);
            Page<Reply> replyPage = replyRepository.getRepliesByCursor(comment, reply.getId(), reply.getCreatedAt(), replyPageable);

            return  ReplyListResponseDto.fromPage(replyPage);
        }else{
            Pageable replyPageable = PageRequest.of(0, REPLY_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "createdAt"));
            Page<Reply> replyPage = replyRepository.findAllByComment(comment, replyPageable);

            return  ReplyListResponseDto.fromPage(replyPage);
        }
    }






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
