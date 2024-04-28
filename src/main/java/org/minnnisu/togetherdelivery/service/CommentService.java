package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.comment.*;
import org.minnnisu.togetherdelivery.dto.post.PostListResponseDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.CommentRepository;
import org.minnnisu.togetherdelivery.repository.PostRepository;
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
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final int PAGE_SIZE = 10;

    public CommentSaveResponseDto saveComment(User user, CommentSaveRequestDto commentSaveRequestDto) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Post post = postRepository.findById(commentSaveRequestDto.getPostId()).
                orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));

        Comment comment = commentRepository.save(Comment.of(post, user, commentSaveRequestDto.getContent()));
        return CommentSaveResponseDto.fromEntity(comment);
    }

    public CommentUpdateResponseDto updateComment(User user, CommentUpdateRequestDto commentUpdateRequestDto) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Comment comment = commentRepository.findById(commentUpdateRequestDto.getCommentId()).
                orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchCommentError));

        if(comment.getDeletedAt() != null) {
            throw new CustomErrorException(ErrorCode.DeletedCommentError);
        }

        if (!comment.getUser().getUsername().equals(user.getUsername())) throw new CustomErrorException(ErrorCode.NotTheAuthorOfTheComment);

        comment.update(commentUpdateRequestDto.getContent());

        return CommentUpdateResponseDto.fromEntity(comment);
    }

    public CommentDeleteResponseDto deleteComment(User user, Long commentId) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchCommentError));

        if(comment.getDeletedAt() != null) {
            throw new CustomErrorException(ErrorCode.DeletedCommentError);
        }

        if (!comment.getUser().getUsername().equals(user.getUsername())) throw new CustomErrorException(ErrorCode.NotTheAuthorOfTheComment);

        comment.delete();

        return CommentDeleteResponseDto.fromEntity(comment);
    }

    public CommentResponseDto getCommentList(int pageNo, Long postId) {
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));

        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Comment> page = commentRepository.findAllByPost(post, pageable);
        return CommentResponseDto.fromPage(page);
    }
}
