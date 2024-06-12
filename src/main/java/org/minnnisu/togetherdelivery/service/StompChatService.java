package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.ChatMessageRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse.*;
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

    public ChatMessageDto sendMessage(ChatMessageRequestDto chatMessageRequestDto, StompPrincipal stompPrincipal) {
        String responsePath = "/topic/chat/room/" + chatMessageRequestDto.getChatRoomId();
        ChatMessageType chatMessageType = chatMessageRequestDto.getType();

        User user = userRepository.findByUsername(chatMessageRequestDto.getUsername())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.UserNotFoundError));

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageRequestDto.getChatRoomId())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));

        ChatRoomMember sender = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchMemberInChatRoomError));


        if (chatMessageType == ChatMessageType.OPEN) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageType));
            ChatMessageOpenResponseDto chatMessageOpenResponseDto = ChatMessageOpenResponseDto.fromEntity(chatMessage);


            return ChatMessageDto.of(responsePath, chatMessageOpenResponseDto);
        }


        if (chatMessageType == ChatMessageType.TALK) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageRequestDto.getMessage(), chatMessageType));
            ChatMessageTalkResponseDto chatMessageTalkResponseDto = ChatMessageTalkResponseDto.fromEntity(chatMessage);

            return ChatMessageDto.of(responsePath, chatMessageTalkResponseDto);
        }

        if (chatMessageType == ChatMessageType.DELETE) {
            ChatMessage chatMessage = chatMessageRepository.findById(chatMessageRequestDto.getDeleteTargetChatMessageId())
                    .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatMessageError));

            StompChatMessageDeleteResponseDto stompChatMessageDeleteResponseDto = StompChatMessageDeleteResponseDto.fromEntity(chatMessage);
            chatMessageRepository.delete(chatMessage);

            return ChatMessageDto.of(responsePath, stompChatMessageDeleteResponseDto);
        }

        if (chatMessageType == ChatMessageType.LEAVE) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageType));
            ChatMessageLeaveResponseDto chatMessageLeaveResponseDto = ChatMessageLeaveResponseDto.fromEntity(chatMessage);

            return ChatMessageDto.of(responsePath, chatMessageLeaveResponseDto);
        }


        throw new CustomErrorException(ErrorCode.UnsupportedMessageTypeError);
    }

    // 초대된 유저 정보 사용
    public ChatMessageDto sendInvitationMessage(ChatRoom chatRoom, ChatRoomMember sender, ChatRoomMember newChatRoomMember) {
        String responsePath = "/topic/chat/room/" + chatRoom.getId();

        ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, ChatMessageType.ENTER));
        ChatMessageEnterResponseDto chatMessageEnterResponseDto = ChatMessageEnterResponseDto.fromEntity(chatMessage, newChatRoomMember);

        return ChatMessageDto.of(responsePath, chatMessageEnterResponseDto);
    }
}

