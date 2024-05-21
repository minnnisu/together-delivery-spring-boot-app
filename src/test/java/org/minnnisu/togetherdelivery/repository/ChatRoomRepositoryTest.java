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
        Category category = categoryRepository.save(getSaveTargetCategory());
        Location location = locationRepository.save(getSaveTargetLocation());
        Post post = postRepository.save(getSaveTargetPost(user, category, location));
        ChatRoom chatRoom = chatRoomRepository.save(getSaveTargetChatRoom(post));

        // when
        Optional<ChatRoom> foundChatRoomOptional = chatRoomRepository.findByPost(post);

        // then
        ChatRoom foundChatRoom = foundChatRoomOptional.get();
        assertThat(foundChatRoom.getId()).isEqualTo(chatRoom.getId());
        assertThat(foundChatRoom.getPost().getId()).isEqualTo(chatRoom.getPost().getId());
        assertThat(foundChatRoom.getCreatedAt()).isSameAs(chatRoom.getCreatedAt());
        assertThat(foundChatRoom.getDeletedAt()).isSameAs(chatRoom.getDeletedAt());
    }

    @DisplayName("채팅방 찾기 - 존재하지 않음")
    @Test
    public void whenFindByPost_thenGetEmptyChatRoom() {
        // given
        User user = userRepository.save(getSaveTargetUser());
        Category category = categoryRepository.save(getSaveTargetCategory());
        Location location = locationRepository.save(getSaveTargetLocation());
        Post post = postRepository.save(getSaveTargetPost(user, category, location));

        // when
        Optional<ChatRoom> foundChatRoomOptional = chatRoomRepository.findByPost(post);

        // then
        assertThat(foundChatRoomOptional).isEmpty();

    }

    private User getSaveTargetUser() {
        return User.of(
                "minnnisu2",
                "user1234#",
                "최민수",
                "유저2",
                "user1234@naver.com",
                false,
                "010-1234-5678",
                false,
                "한끼대학교",
                PasswordEncoderFactories.createDelegatingPasswordEncoder()
        );
    }

    private Category getSaveTargetCategory() {
        MealCategoryCode mealCategoryCode = MealCategoryCode.AMERICAN_FOOD;

        return Category.of(
                mealCategoryCode.name(),
                mealCategoryCode.getCategoryName()
        );
    }

    private Location getSaveTargetLocation() {
        return Location.of(
                "대한민국 경상남도 창원시 의창구 의창대로 67",
                "창원역",
                35.2575221,
                128.6074553
        );
    }

    private Post getSaveTargetPost(User user, Category category, Location location) {
        return Post.of(
                user,
                "저번에 먹어봤는데 맛있었어요",
                "한끼 치킨",
                category,
                location,
                15000,
                3000
        );
    }

    private ChatRoom getSaveTargetChatRoom(Post post) {
        return ChatRoom.of(post);
    }
}