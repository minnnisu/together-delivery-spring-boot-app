package org.minnnisu.togetherdelivery.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatRoomInviteResponseDto {
    private Long chatRoomId;
    private List<String> invitedMembers;

    public static ChatRoomInviteResponseDto fromEntity(List<ChatRoomMember> chatRoomMembers){
        ChatRoomMember chatRoomMember = chatRoomMembers.get(0);

        return ChatRoomInviteResponseDto.builder()
                .chatRoomId(chatRoomMember.getChatRoom().getId())
                .invitedMembers(chatRoomMembers.stream().map(chatRoomMember1 -> chatRoomMember1.getUser().getNickname()).toList())
                .build();
    }
}
