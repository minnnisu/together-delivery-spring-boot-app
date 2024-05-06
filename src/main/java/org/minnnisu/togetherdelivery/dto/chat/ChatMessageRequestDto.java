package org.minnnisu.togetherdelivery.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.MessageType;

@Getter
@Setter
public class ChatMessageRequestDto {
    private MessageType type;
    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String sender;
    //내용
    private String message;
}
