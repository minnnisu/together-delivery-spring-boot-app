package org.minnnisu.togetherdelivery.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Reply;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreatorDto {
    private String creator;
    private String content;

    public static CreatorDto fromEntity(Comment comment){
        return CreatorDto.builder()
                .creator(comment.getUser().getNickname())
                .content(comment.getContent())
                .build();
    }

    public static CreatorDto fromEntity(Reply reply){
        return CreatorDto.builder()
                .creator(reply.getUser().getNickname())
                .content(reply.getContent())
                .build();
    }
}
