package org.minnnisu.togetherdelivery.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minnnisu.togetherdelivery.constant.MealCategoryCode;
import org.minnnisu.togetherdelivery.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.minnnisu.togetherdelivery.common.DummyData.*;

@DataJpaTest
class ChatRoomRepositoryTest {
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

    @DisplayName("채팅방 찾기 - 존재함")
    @Test
    void whenFindByPost_thenGetChatRoom() {
        // given
        User user = userRepository.save(getSaveTargetUser());
        Category category = categoryRepository.save(getSaveTargetAmericanFoodCategory());
        Location location = locationRepository.save(getSaveTargetLocation());
        Post post = postRepository.save(getSaveTargetPost(user, category, location));
        ChatRoom chatRoom = chatRoomRepository.save(getSaveTargetChatRoom(post));

        // when
        Optional<ChatRoom> foundChatRoomOptional = chatRoomRepository.findByPost(post);

        // then
        ChatRoom foundChatRoom = foundChatRoomOptional.get();

        assertThat(foundChatRoom).isSameAs(chatRoom);
    }

    @DisplayName("채팅방 찾기 - 존재하지 않음")
    @Test
    public void whenFindByPost_thenGetEmptyChatRoom() {
        // given
        User user = userRepository.save(getSaveTargetUser());
        Category category = categoryRepository.save(getSaveTargetAmericanFoodCategory());
        Location location = locationRepository.save(getSaveTargetLocation());
        Post post = postRepository.save(getSaveTargetPost(user, category, location));

        // when
        Optional<ChatRoom> foundChatRoomOptional = chatRoomRepository.findByPost(post);

        // then
        assertThat(foundChatRoomOptional).isEmpty();
    }
}