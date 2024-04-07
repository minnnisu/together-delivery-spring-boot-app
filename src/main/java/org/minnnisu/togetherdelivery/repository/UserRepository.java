package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String name);
    Optional<User> findByNickname(String nickname);
}