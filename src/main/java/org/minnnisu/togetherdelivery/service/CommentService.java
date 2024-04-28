package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.comment.CommentSaveRequestDto;
import org.minnnisu.togetherdelivery.dto.comment.CommentSaveResponseDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.CommentRepository;
import org.minnnisu.togetherdelivery.repository.PostRepository;
import org.minnnisu.togetherdelivery.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentSaveResponseDto saveComment(User user, CommentSaveRequestDto commentSaveRequestDto) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Post post = postRepository.findById(commentSaveRequestDto.getPostId()).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));
        Comment comment = commentRepository.save(Comment.of(post, user, commentSaveRequestDto.getContent()));
        return CommentSaveResponseDto.fromEntity(comment);
    }
}
