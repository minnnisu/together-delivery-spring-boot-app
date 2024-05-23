package org.minnnisu.togetherdelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.*;
import org.minnnisu.togetherdelivery.dto.chat.*;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.*;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoomListResponseDto getChatRoomList(User user) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserPermissionDeniedError);
        }

        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findAllByUser(user);
        return ChatRoomListResponseDto.fromEntity(chatRoomMembers);
    }

    public ChatRoomInviteResponseDto inviteMember(ChatRoomInviteRequestDto chatRoomInviteRequestDto, User user) {
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

        return ChatRoomInviteResponseDto.fromEntity(newChatRoomMember);
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
