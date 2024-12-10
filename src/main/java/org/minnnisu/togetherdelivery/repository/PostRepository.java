package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.Category;
import org.minnnisu.togetherdelivery.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
                SELECT p FROM Post p
                WHERE (:cursor IS NULL OR p.id < :cursor)
                AND (:status IS NULL OR p.status = :status)
                AND (:category IS NULL OR p.category = :category)
                AND (p.user IS NULL OR p.user.college = :college)
                ORDER BY p.id DESC
           """)
    Page<Post> findPosts(Long cursor, Boolean status, Category category, String college, Pageable pageable);
}