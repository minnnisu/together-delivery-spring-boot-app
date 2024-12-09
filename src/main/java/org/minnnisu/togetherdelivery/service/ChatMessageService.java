package org.minnnisu.togetherdelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.message.ChatMessageListDto;
import org.minnnisu.togetherdelivery.dto.stomp.chatMessage.chatMessageDelete.ChatMessageDeleteDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.ChatMessageRepository;
import org.minnnisu.togetherdelivery.repository.ChatRoomMemberRepository;
import org.minnnisu.togetherdelivery.repository.ChatRoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageDeleteDto deleteChatMessage(Long chatMessageId, User user) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserPermissionDeniedError);
        }

        ChatMessage deleteTargetchatMessage = chatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatMessageError));

        User creator = deleteTargetchatMessage.getSender().getUser();
        if (!Objects.equals(creator.getId(), user.getId())) {
            throw new CustomErrorException(ErrorCode.NotTheSenderOfChatMessage);
        }

        ChatMessageDeleteDto chatMessageDeleteDto = ChatMessageDeleteDto.fromEntity(deleteTargetchatMessage);

        chatMessageRepository.delete(deleteTargetchatMessage);

        return chatMessageDeleteDto;

    }

    public ChatMessageListDto getChatMessageList(User user, Long cursor, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));
        chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchMemberInChatRoomError));

        Page<ChatMessage> chatMessagePage;
        Pageable chatMessagePageable = PageRequest.of(0, 20);

        if (cursor != null) {
            chatMessagePage = chatMessageRepository.getChatMessagesByCursor(chatRoom, cursor, chatMessagePageable);
        } else {
            chatMessagePage = chatMessageRepository.findLatestMessagesByChatRoom(chatRoom, chatMessagePageable);
        }
        return ChatMessageListDto.fromPage(chatMessagePage);
    }
}
