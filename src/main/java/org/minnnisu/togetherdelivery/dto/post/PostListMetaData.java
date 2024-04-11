package org.minnnisu.togetherdelivery.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostListMetaData {
    private int totalPage;
    private int currentPage;

    public static PostListMetaData of(int totalPage, int currentPage){
        return PostListMetaData.builder()
                .totalPage(totalPage)
                .currentPage(currentPage + 1)
                .build();
    }
}
