package org.minnnisu.togetherdelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.*;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomAccess.ChatRoomAccessDto;
import org.minnnisu.togetherdelivery.dto.stomp.chatMessage.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitResponseDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomEnter.ChatRoomEnterDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomList.ChatRoomListResponseDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final StompChatService stompChatService;


    public ChatRoomListResponseDto getChatRoomList(User user) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserPermissionDeniedError);
        }

        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findAllByUser(user);
        return ChatRoomListResponseDto.fromEntity(chatRoomMembers);
    }

    public ChatRoomExitResponseDto exitChatRoom(ChatRoomExitRequestDto chatRoomExitRequestDto, User user) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserPermissionDeniedError);
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomExitRequestDto.getChatRoomId())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));
        ChatRoomMember deleteTargetChatRoomMember = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchMemberInChatRoomError));

        List<ChatMessage> chatMessages = chatMessageRepository.findAllBySender(deleteTargetChatRoomMember);
        for(ChatMessage chatMessage: chatMessages){
            chatMessage.updateSenderNull();
        }

        ChatRoomExitResponseDto chatRoomExitResponseDto = ChatRoomExitResponseDto.fromEntity(deleteTargetChatRoomMember);
        chatRoomMemberRepository.delete(deleteTargetChatRoomMember);

        return chatRoomExitResponseDto;
    }

    public ChatRoomEnterDto enterChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));

        if(!chatRoom.getPost().isStatus()) {
            throw new CustomErrorException(ErrorCode.ClosedPostError);
        }

        if (chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user).isPresent()) {
            throw new CustomErrorException(ErrorCode.AlreadyExistChatRoomMemberError);
        }

        ChatRoomMember newChatRoomMember = chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, user));

        ChatMessageDto chatMessageDto = stompChatService.sendInvitationMessage(chatRoom, newChatRoomMember, newChatRoomMember);

        return ChatRoomEnterDto.of(newChatRoomMember, chatMessageDto);
    }

    public ChatRoomAccessDto accessChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));

        Optional<ChatRoomMember> chatRoomMemberOptional = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user);

        if (chatRoomMemberOptional.isPresent()) {
            return ChatRoomAccessDto.of(true);
        }

        return ChatRoomAccessDto.of(false);
    }
}
