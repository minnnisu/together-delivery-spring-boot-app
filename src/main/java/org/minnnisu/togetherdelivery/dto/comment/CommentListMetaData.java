package org.minnnisu.togetherdelivery.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentListMetaData {
    private int totalPage;
    private int currentPage;

    public static CommentListMetaData of(int totalPage, int currentPage){
        return CommentListMetaData.builder()
                .totalPage(totalPage)
                .currentPage(currentPage + 1)
                .build();
    }
}
