package org.minnnisu.togetherdelivery.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.minnnisu.togetherdelivery.common.DummyData.*;

@DataJpaTest
class ChatMessageRepositoryTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    @DisplayName("Sender를 통해 채팅메시지 가져오기")
    void whenFindAllBySender_thenReturnChatMessage(){
        // given
        User user = userRepository.save(getSaveTargetUser());
        Category americanFoodCategory = categoryRepository.save(getSaveTargetAmericanFoodCategory());
        Location location = locationRepository.save(getSaveTargetLocation());
        Post post = postRepository.save(getSaveTargetPost(user, americanFoodCategory, location));
        ChatRoom chatRoom = chatRoomRepository.save(getSaveTargetChatRoom(post));
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.save(getSaveTargetChatRoomMember(chatRoom, user));
        ChatMessage chatMessage1 = chatMessageRepository.save(getSaveTargetChatMessage(chatRoomMember, "채팅방이 생성되었습니다.", ChatMessageType.OPEN));
        ChatMessage chatMessage2 = chatMessageRepository.save(getSaveTargetChatMessage(chatRoomMember, "하이", ChatMessageType.TALK));
        ChatMessage chatMessage3 = chatMessageRepository.save(getSaveTargetChatMessage(chatRoomMember, "오랜만", ChatMessageType.TALK));


        // when
        List<ChatMessage> chatMessages = chatMessageRepository.findAllBySender(chatRoomMember);

        // then
        assertThat(chatMessages.size()).isEqualTo(3);
        assertThat(chatMessages.get(0)).isSameAs(chatMessage1);
        assertThat(chatMessages.get(1)).isSameAs(chatMessage2);
        assertThat(chatMessages.get(2)).isSameAs(chatMessage3);

    }

}