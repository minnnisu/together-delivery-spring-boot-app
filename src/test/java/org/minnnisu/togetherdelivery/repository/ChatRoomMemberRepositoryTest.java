package org.minnnisu.togetherdelivery.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minnnisu.togetherdelivery.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.minnnisu.togetherdelivery.common.DummyData.*;

@DataJpaTest
class ChatRoomMemberRepositoryTest {
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

    @DisplayName("User로 ChatRoomMember 찾기")
    @Test
    void whenFindAllByUser_thenReturnChatRoomMembers(){
        // given
        User user = userRepository.save(getSaveTargetUser());

        Category americanFoodCategory = categoryRepository.save(getSaveTargetAmericanFoodCategory());
        Category pizzaCategory = categoryRepository.save(getSaveTargetPizzaCategory());
        Category japaneseFoodCategory = categoryRepository.save(getSaveTargetJapaneseFoodCategory());

        Location location = locationRepository.save(getSaveTargetLocation());
        Location location2 = locationRepository.save(getSaveTargetLocation2());
        Location location3 = locationRepository.save(getSaveTargetLocation3());

        Post post = postRepository.save(getSaveTargetPost(user, americanFoodCategory, location));
        Post post2 = postRepository.save(getSaveTargetPost(user, pizzaCategory, location2));
        Post post3 = postRepository.save(getSaveTargetPost(user, japaneseFoodCategory, location3));

        ChatRoom chatRoom = chatRoomRepository.save(getSaveTargetChatRoom(post));
        ChatRoom chatRoom2 = chatRoomRepository.save(getSaveTargetChatRoom(post2));
        ChatRoom chatRoom3 = chatRoomRepository.save(getSaveTargetChatRoom(post3));

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, user));
        ChatRoomMember chatRoomMember2 = chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom2, user));
        ChatRoomMember chatRoomMember3 = chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom3, user));

        // when
        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findAllByUser(user);

        // then
        assertThat(chatRoomMembers.size()).isEqualTo(3);
        assertThat(chatRoomMembers.get(0)).isSameAs(chatRoomMember);
        assertThat(chatRoomMembers.get(1)).isSameAs(chatRoomMember2);
        assertThat(chatRoomMembers.get(2)).isSameAs(chatRoomMember3);
    }

    @DisplayName("ChatRoom과 User로 ChatRoomMember 찾기 - 존재함")
    @Test
    void whenFindByChatRoomAndUser_thenReturnChatRoomMember(){
        // given
        User user = userRepository.save(getSaveTargetUser());
        Category americanFoodCategory = categoryRepository.save(getSaveTargetAmericanFoodCategory());
        Location location = locationRepository.save(getSaveTargetLocation());
        Post post = postRepository.save(getSaveTargetPost(user, americanFoodCategory, location));
        ChatRoom chatRoom = chatRoomRepository.save(getSaveTargetChatRoom(post));
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, user));

        // when
        Optional<ChatRoomMember> foundChatRoomMemberOptional = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user);

        // then
        assertThat(foundChatRoomMemberOptional.get()).isSameAs(chatRoomMember);
    }

    @DisplayName("ChatRoom과 User로 ChatRoomMember 찾기 - 존재하지 않음")
    @Test
    void whenFindByChatRoomAndUser_thenReturnEmpty(){
        // given
        User user = userRepository.save(getSaveTargetUser());
        Category americanFoodCategory = categoryRepository.save(getSaveTargetAmericanFoodCategory());
        Location location = locationRepository.save(getSaveTargetLocation());
        Post post = postRepository.save(getSaveTargetPost(user, americanFoodCategory, location));
        ChatRoom chatRoom = chatRoomRepository.save(getSaveTargetChatRoom(post));

        // when
        Optional<ChatRoomMember> foundChatRoomMemberOptional = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user);

        // then
        assertThat(foundChatRoomMemberOptional).isEmpty();
    }
}