package org.minnnisu.togetherdelivery.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.MealCategoryCode;
import org.minnnisu.togetherdelivery.domain.Category;
import org.minnnisu.togetherdelivery.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyCommandLineRunner implements CommandLineRunner {
    final private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
//        initializeDefaultCategory();
//        log.info("초기 카테고리 데이터 생성");
    }

    public void initializeDefaultCategory(){
        for (MealCategoryCode mealCategoryCode : MealCategoryCode.values()) {
            categoryRepository.save(
                    Category.of(
                            mealCategoryCode.name(),
                            mealCategoryCode.getCategoryName())
            );
        }
    }
}
