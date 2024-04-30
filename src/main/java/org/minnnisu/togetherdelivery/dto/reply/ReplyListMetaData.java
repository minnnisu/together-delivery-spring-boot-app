package org.minnnisu.togetherdelivery.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReplyListMetaData {
    private int totalPage;
    private int currentPage;

    public static ReplyListMetaData of(int totalPage, int currentPage){
        return ReplyListMetaData.builder()
                .totalPage(totalPage)
                .currentPage(currentPage + 1)
                .build();
    }
}
