package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.minnnisu.togetherdelivery.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findAllByUser(User user);
    Optional<ChatRoomMember> findByChatRoomAndUser(ChatRoom chatRoom, User user);
}
