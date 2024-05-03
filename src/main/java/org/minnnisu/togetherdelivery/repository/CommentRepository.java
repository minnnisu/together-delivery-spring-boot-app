package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPost(Post post, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.post = :post AND ((c.id > :commentId AND c.createdAt = :createdAt) OR c.createdAt > :createdAt)")
    Page<Comment> getCommentsByCursor(@Param("post") Post post,
                                   @Param("commentId") Long commentId,
                                   @Param("createdAt") LocalDateTime createdAt,
                                   Pageable replyPageable
    );
}
