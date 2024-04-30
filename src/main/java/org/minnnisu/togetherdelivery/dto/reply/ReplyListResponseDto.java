package org.minnnisu.togetherdelivery.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Reply;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReplyListResponseDto {
    private ReplyListMetaData metaData;
    private List<ReplyDto> replies;

    public static ReplyListResponseDto fromPage(Page<Reply> page){
        return ReplyListResponseDto.builder()
                .metaData(ReplyListMetaData.of(page.getTotalPages(), page.getNumber()))
                .replies(page.map(ReplyDto::fromEntity).toList())
                .build();
    }
}
