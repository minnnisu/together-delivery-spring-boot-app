package org.minnnisu.togetherdelivery.common;

import org.minnnisu.togetherdelivery.constant.MealCategoryCode;
import org.minnnisu.togetherdelivery.domain.*;

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
}
