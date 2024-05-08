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

        User user = userRepository.findById(chatMessageRequestDto.getSenderUsername())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.UserNotFoundError));

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageRequestDto.getChatRoomId())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));

        ChatRoomMember sender = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));


        if (chatMessageType == ChatMessageType.OPEN) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageType));
            ChatMessageOpenResponseDto chatMessageOpenResponseDto = ChatMessageOpenResponseDto.fromEntity(chatMessage);


            return ChatMessageDto.of(responsePath, chatMessageOpenResponseDto);
        }

        if (chatMessageType == ChatMessageType.ENTER) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageType));
            ChatMessageEnterResponseDto chatMessageEnterResponseDto = ChatMessageEnterResponseDto.fromEntity(chatMessage);

            return ChatMessageDto.of(responsePath, chatMessageEnterResponseDto);
        }

        if (chatMessageType == ChatMessageType.TALK) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageRequestDto.getMessage(), chatMessageType));
            ChatMessageTalkResponseDto chatMessageTalkResponseDto = ChatMessageTalkResponseDto.fromEntity(chatMessage);

            return ChatMessageDto.of(responsePath, chatMessageTalkResponseDto);
        }

        if (chatMessageType == ChatMessageType.DELETE) {
            ChatMessage chatMessage = chatMessageRepository.findById(chatMessageRequestDto.getChatMessageId())
                    .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchCHatMessageError));

            ChatMessageDeleteResponseDto chatMessageDeleteResponseDto = ChatMessageDeleteResponseDto.fromEntity(chatMessage);
            chatMessageRepository.delete(chatMessage);

            return ChatMessageDto.of(responsePath, chatMessageDeleteResponseDto);
        }

        if (chatMessageType == ChatMessageType.LEAVE) {
            ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.of(sender, chatMessageType));
            ChatMessageLeaveResponseDto chatMessageLeaveResponseDto = ChatMessageLeaveResponseDto.fromEntity(chatMessage);

            return ChatMessageDto.of(responsePath, chatMessageLeaveResponseDto);
        }


        throw new CustomErrorException(ErrorCode.UnsupportedMessageTypeError);
    }
}

