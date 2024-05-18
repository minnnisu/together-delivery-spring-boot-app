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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoomCreateResponseDto createRoom(ChatRoomCreateRequestDto chatRoomCreateRequestDto, User user) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserPermissionDeniedError);
        }

        Post post = postRepository.findById(chatRoomCreateRequestDto.getPostId())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));

        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByPost(post);
        if (chatRoomOptional.isPresent()) {
            throw new CustomErrorException(ErrorCode.AlreadyExistChatRoomError);
        }

        ChatRoom newChatRoom = chatRoomRepository.save(ChatRoom.of(post));

        ChatRoomMember chatRoomCreator = chatRoomMemberRepository.save(ChatRoomMember.createChatRoomCreator(newChatRoom, user));

        chatMessageRepository.save(
                ChatMessage.of(
                        chatRoomCreator,
                        "채팅방이 생성되었습니다.",
                        ChatMessageType.OPEN));

        return ChatRoomCreateResponseDto.fromEntity(chatRoomCreator);
    }

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
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchMemberInChatRoomError));
        chatMessageRepository.deleteAllBySender(chatRoomMember);

        ChatRoomExitResponseDto chatRoomExitResponseDto = ChatRoomExitResponseDto.fromEntity(chatRoomMember);
        chatRoomMemberRepository.delete(chatRoomMember);

        return chatRoomExitResponseDto;
    }
}
