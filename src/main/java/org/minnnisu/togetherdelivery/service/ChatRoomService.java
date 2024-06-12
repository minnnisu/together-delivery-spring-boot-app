package org.minnnisu.togetherdelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.*;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitResponseDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomInvite.ChatRoomInviteDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomInvite.ChatRoomInviteRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomList.ChatRoomListResponseDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final UserRepository userRepository;
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

    public ChatRoomInviteDto inviteMember(ChatRoomInviteRequestDto chatRoomInviteRequestDto, User user) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserPermissionDeniedError);
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomInviteRequestDto.getChatRoomId())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));

        ChatRoomMember chatRoomCreator = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchMemberInChatRoomError));
        if(!chatRoomCreator.isCreator()) {
            throw new CustomErrorException(ErrorCode.ChatInvitePermissionDeniedError);
        }

        User inviteTargetMember = userRepository.findByNickname(chatRoomInviteRequestDto.getInvitedMember()).orElseThrow(() -> new CustomErrorException(ErrorCode.UserNotFoundError));
        if (chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, inviteTargetMember).isPresent()) {
            throw new CustomErrorException(ErrorCode.AlreadyExistChatRoomMemberError);
        }
        ChatRoomMember newChatRoomMember = chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, inviteTargetMember));

        ChatMessageDto chatMessageDto = stompChatService.sendInvitationMessage(chatRoom, chatRoomCreator, newChatRoomMember);

        return ChatRoomInviteDto.of(newChatRoomMember, chatMessageDto);
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
}
