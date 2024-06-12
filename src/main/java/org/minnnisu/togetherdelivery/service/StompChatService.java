package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.chatMessageInvite.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.stompChatMessage.*;
import org.minnnisu.togetherdelivery.dto.chat.stompChatMessage.*;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.handler.AssignPrincipalHandshakeHandler.StompPrincipal;
import org.minnnisu.togetherdelivery.repository.ChatMessageRepository;
import org.minnnisu.togetherdelivery.repository.ChatRoomMemberRepository;
import org.minnnisu.togetherdelivery.repository.ChatRoomRepository;
import org.minnnisu.togetherdelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StompChatService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageDto sendMessage(StompChatMessageRequestDto stompChatMessageRequestDto, StompPrincipal stompPrincipal) {
        String responsePath = "/topic/chat/room/" + stompChatMessageRequestDto.getChatRoomId();
        ChatMessageType chatMessageType = stompChatMessageRequestDto.getType();

        User user = userRepository.findByUsername(stompChatMessageRequestDto.getUsername())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.UserNotFoundError));

        ChatRoom chatRoom = chatRoomRepository.findById(stompChatMessageRequestDto.getChatRoomId())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));

        ChatRoomMember sender = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchMemberInChatRoomError));


        if (chatMessageType == ChatMessageType.OPEN) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageType));
            StompChatMessageOpenResponseDto stompChatMessageOpenResponseDto = StompChatMessageOpenResponseDto.fromEntity(chatMessage);


            return ChatMessageDto.of(responsePath, stompChatMessageOpenResponseDto);
        }


        if (chatMessageType == ChatMessageType.TALK) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, stompChatMessageRequestDto.getMessage(), chatMessageType));
            StompChatMessageTalkResponseDto stompChatMessageTalkResponseDto = StompChatMessageTalkResponseDto.fromEntity(chatMessage);

            return ChatMessageDto.of(responsePath, stompChatMessageTalkResponseDto);
        }

        if (chatMessageType == ChatMessageType.DELETE) {
            ChatMessage chatMessage = chatMessageRepository.findById(stompChatMessageRequestDto.getDeleteTargetChatMessageId())
                    .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatMessageError));

            StompChatMessageDeleteResponseDto stompChatMessageDeleteResponseDto = StompChatMessageDeleteResponseDto.fromEntity(chatMessage);
            chatMessageRepository.delete(chatMessage);

            return ChatMessageDto.of(responsePath, stompChatMessageDeleteResponseDto);
        }

        if (chatMessageType == ChatMessageType.LEAVE) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageType));
            StompChatMessageLeaveResponseDto stompChatMessageLeaveResponseDto = StompChatMessageLeaveResponseDto.fromEntity(chatMessage);

            return ChatMessageDto.of(responsePath, stompChatMessageLeaveResponseDto);
        }


        throw new CustomErrorException(ErrorCode.UnsupportedMessageTypeError);
    }

    // 초대된 유저 정보 사용
    public ChatMessageDto sendInvitationMessage(ChatRoom chatRoom, ChatRoomMember sender, ChatRoomMember newChatRoomMember) {
        String responsePath = "/topic/chat/room/" + chatRoom.getId();

        ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, ChatMessageType.ENTER));
        StompChatMessageEnterResponseDto stompChatMessageEnterResponseDto = StompChatMessageEnterResponseDto.fromEntity(chatMessage, newChatRoomMember);

        return ChatMessageDto.of(responsePath, stompChatMessageEnterResponseDto);
    }
}

