package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String categoryCode;

    private String categoryName;

    public static Category of(
            String categoryCode,
            String categoryName
    ){
        return Category.builder()
                .categoryCode(categoryCode)
                .categoryName(categoryName)
                .build();
    }
}
