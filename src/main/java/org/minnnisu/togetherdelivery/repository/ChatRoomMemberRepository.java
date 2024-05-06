package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
