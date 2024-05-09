package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.Reply;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.comment.*;
import org.minnnisu.togetherdelivery.dto.post.PostListResponseDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.CommentRepository;
import org.minnnisu.togetherdelivery.repository.PostRepository;
import org.minnnisu.togetherdelivery.repository.ReplyRepository;
import org.minnnisu.togetherdelivery.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    private final int COMMENT_PAGE_SIZE = 10;
    private final int REPLY_PAGE_SIZE = 5;

    public CommentResponseDto getCommentList(Long cursor, Long postId) {
        Page<Comment> commentPage;

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));

        Pageable commentPageable = PageRequest.of(0, COMMENT_PAGE_SIZE);

        if(cursor != null) {
            Comment comment = commentRepository.findById(cursor)
                    .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchCommentError));
            commentPage = commentRepository.getCommentsByCursor(post, comment.getId(), comment.getCreatedAt(), commentPageable);
        }else {
            commentPage = commentRepository.findAllByPost(post, commentPageable);
        }


        Pageable replyPageable = PageRequest.of(0, REPLY_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "createdAt"));
        List<CommentListItemDto> commentList = commentPage.map(comment1 -> CommentListItemDto.of(comment1, replyRepository.findAllByComment(comment1, replyPageable))).toList();

        return CommentResponseDto.of(commentList);
    }

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

    public CommentCreatorResponseDto getCommentCreators(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));

        List<Reply> replies = new ArrayList<>();

        List<Comment> comments = commentRepository.findAllByPost(post);
        for (Comment comment : comments) {
            replies.addAll(replyRepository.findAllByComment(comment));
        }

        comments.sort(Comparator.comparing(Comment::getCreatedAt).reversed());
        comments = removeDuplicatedCreator(comments, comment -> comment.getUser().getId());

        replies.sort(Comparator.comparing(Reply::getCreatedAt).reversed());
        replies = removeDuplicatedCreator(replies, reply -> reply.getUser().getId());

        return CommentCreatorResponseDto.fromEntity(comments, replies);
    }

    private <T> List<T> removeDuplicatedCreator(List<T> items, Function<T, Long> getUserId) {
        List<T> uniqueItems = new ArrayList<>();
        Set<Long> creatorIds = new HashSet<>();

        for (T item : items) {
            Long targetId = getUserId.apply(item);
            if (!creatorIds.contains(targetId)) {
                uniqueItems.add(item);
                creatorIds.add(targetId);
            }
        }

        return uniqueItems;
    }
}
