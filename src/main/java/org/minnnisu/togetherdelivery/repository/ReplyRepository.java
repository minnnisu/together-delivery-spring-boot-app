package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findAllByComment(Comment comment, Pageable pageable);

    @Query("SELECT r FROM Reply r WHERE r.comment = :comment AND ((r.id > :replyId AND r.createdAt = :createdAt) OR r.createdAt > :createdAt)")
    Page<Reply> getRepliesByCursor(@Param("comment") Comment comment,
                                   @Param("replyId") Long replyId,
                                   @Param("createdAt") LocalDateTime createdAt,
                                   Pageable replyPageable
    );
}
