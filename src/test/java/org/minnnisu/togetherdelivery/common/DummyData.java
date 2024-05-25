package org.minnnisu.togetherdelivery.common;

import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.constant.MealCategoryCode;
import org.minnnisu.togetherdelivery.domain.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.time.LocalDateTime;

public class DummyData {
    public static User createUser() {
        return new User(
                1L,
                "minnnisu",
                "user1234#",
                "최민수",
                "유저1",
                "user1234@naver.com",
                false,
                "010-1234-5678",
                false,
                "한끼대학교",
                "USER",
                LocalDateTime.of(2024,1,1,1,1,1),
                LocalDateTime.of(2024,1,1,1,1,1),
                null);
    }

    public static User createUser2() {
        return new User(
                1L,
                "minnnisu2",
                "user1234#",
                "최민수",
                "유저2",
                "user1234@naver.com",
                false,
                "010-1234-5678",
                false,
                "한끼대학교",
                "USER",
                LocalDateTime.of(2024,1,1,1,1,1),
                LocalDateTime.of(2024,1,1,1,1,1),
                null);
    }

    public static Post createPost() {
        return Post.of(
                1L,
                createUser(),
                "한끼 가게",
                createCategory(MealCategoryCode.AMERICAN_FOOD),
                createLocation(),
                20000,
                3000,
                "쿠폰을 주고 있어요",
                true,
                LocalDateTime.of(2024,1,1,1,1,1),
                LocalDateTime.of(2024,1,1,1,1,1),
                null
        );
    }

    public static Post createPost2() {
        return Post.of(
                2L,
                createUser2(),
                "한끼 치킨",
                createCategory(MealCategoryCode.CHICKEN),
                createLocation(),
                15000,
                3000,
                "저번에 먹어봤는데 맛있었어요",
                true,
                LocalDateTime.of(2024,1,1,1,1,1),
                LocalDateTime.of(2024,1,1,1,1,1),
                null
        );
    }

    public static Category createCategory(MealCategoryCode mealCategoryCode){
        return Category.of(
                1L,
                mealCategoryCode.name(),
                mealCategoryCode.getCategoryName()
        );
    }


    public static Location createLocation(){
        return Location.of(
                1L,
                "대한민국 경상남도 창원시 의창구 의창대로 67",
                "창원역",
                35.2575221,
                128.6074553,
                LocalDateTime.of(2024,1,1,1,1,1),
                LocalDateTime.of(2024,1,1,1,1,1),
                null
        );
    }

    public static ChatRoom createChatRoom() {
        return ChatRoom.of(1L, createPost(), LocalDateTime.of(2024,1,1,1,1,1), null);
    }

    public static ChatRoom createChatRoom2() {
        return ChatRoom.of(2L, createPost2(), LocalDateTime.of(2024,1,1,1,1,1), null);
    }

    public static ChatRoomMember createChatRoomMember(boolean isCreator) {
        return ChatRoomMember.of(1L, createChatRoom(), createUser(), isCreator, LocalDateTime.of(2024,1,1,1,1,1), null);
    }

    public static ChatRoomMember createChatRoomMember2(boolean isCreator) {
        return ChatRoomMember.of(2L, createChatRoom2(), createUser(), isCreator, LocalDateTime.of(2024,1,1,1,1,1), null);
    }

    public static ChatMessage createChatMessage(ChatMessageType chatMessageType){
        return ChatMessage.of(createChatRoomMember(false), "message", chatMessageType);
    }


    public static User getSaveTargetUser() {
        return User.of(
                "minnnisu",
                "user1234#",
                "최민수",
                "유저",
                "user1234@naver.com",
                false,
                "010-1234-5678",
                false,
                "한끼대학교",
                PasswordEncoderFactories.createDelegatingPasswordEncoder()
        );
    }

    public static User getSaveTargetUser2() {
        return User.of(
                "minnnisu2",
                "user12345#",
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

    public static User getSaveTargetUser3() {
        return User.of(
                "minnnisu3",
                "user12346#",
                "최민수",
                "유저3",
                "user1234@naver.com",
                false,
                "010-1234-5678",
                false,
                "한끼대학교",
                PasswordEncoderFactories.createDelegatingPasswordEncoder()
        );
    }


    public static Category getSaveTargetAmericanFoodCategory() {
        MealCategoryCode mealCategoryCode = MealCategoryCode.AMERICAN_FOOD;

        return Category.of(
                mealCategoryCode.name(),
                mealCategoryCode.getCategoryName()
        );
    }

    public static Category getSaveTargetPizzaCategory() {
        MealCategoryCode mealCategoryCode = MealCategoryCode.PIZZA;

        return Category.of(
                mealCategoryCode.name(),
                mealCategoryCode.getCategoryName()
        );
    }

    public static Category getSaveTargetJapaneseFoodCategory() {
        MealCategoryCode mealCategoryCode = MealCategoryCode.JAPANESE_FOOD;

        return Category.of(
                mealCategoryCode.name(),
                mealCategoryCode.getCategoryName()
        );
    }

    public static Location getSaveTargetLocation() {
        return Location.of(
                "대한민국 경상남도 창원시 의창구 의창대로 1",
                "창원역",
                35.2575220,
                128.6074550
        );
    }

    public static Location getSaveTargetLocation2() {
        return Location.of(
                "대한민국 경상남도 창원시 의창구 의창대로 2",
                "창원중앙역",
                35.2575221,
                128.6074551
        );
    }

    public static Location getSaveTargetLocation3() {
        return Location.of(
                "대한민국 경상남도 창원시 의창구 의창대로 3",
                "경남은행",
                35.2575222,
                128.6074552
        );
    }



    public static Post getSaveTargetPost(User user, Category category, Location location) {
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

    public static Post getSaveTargetPost2(User user, Category category, Location location) {
        return Post.of(
                user,
                "저번에 먹어봤는데 맛있었어요",
                "한끼 피자",
                category,
                location,
                15000,
                3000
        );
    }

    public static Post getSaveTargetPos3(User user, Category category, Location location) {
        return Post.of(
                user,
                "저번에 먹어봤는데 맛있었어요",
                "한끼 라멘",
                category,
                location,
                15000,
                3000
        );
    }

    public static ChatRoom getSaveTargetChatRoom(Post post) {
        return ChatRoom.of(post);
    }

    public static ChatRoomMember getSaveTargetChatRoomMember(ChatRoom chatRoom, User user){
        return ChatRoomMember.of(chatRoom, user);
    }

    public static ChatMessage getSaveTargetChatMessage(ChatRoomMember sender, String message, ChatMessageType chatMessageType){
        return ChatMessage.of(sender, message, chatMessageType);
    }
}
