package org.minnnisu.togetherdelivery.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.minnnisu.togetherdelivery.domain.Category;

@AllArgsConstructor
@Getter
public enum MealCategoryCode {
    PORK_FEET_BOSSAM("족발/보쌈"),
    KOREAN_SOUP("찜/탕/찌개"),
    JAPANESE_FOOD("돈까스/회/일식"),
    PIZZA("피자"),
    MEAT("고기/구이"),
    NIGHT_FOOD("야식"),
    AMERICAN_FOOD("양식"),
    CHICKEN("치킨"),
    CHINESE_FOOD("중식"),
    ASIAN_FOOD("아시안"),
    BAEBAN_WATER_GREUl_NOODLE("백반/죽/국수"),
    LUNCH_BOX("도시락"),
    SNACK_BAR("분식"),
    CAFE("카페/디저트"),
    FAST_FOOD("패스트푸드"),
    ETC("기타");

    private String categoryName;

    public static MealCategoryCode FromEntity(Category category) {
        MealCategoryCode mealCategoryCode = ETC;

        String categoryCode = category.getCategoryCode();
        for (MealCategoryCode _mealCategoryCode : MealCategoryCode.values()) {
           if(_mealCategoryCode.name().equals(categoryCode)){
               mealCategoryCode = _mealCategoryCode;
           }
        }

        return mealCategoryCode;
    }
}
