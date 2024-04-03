package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}