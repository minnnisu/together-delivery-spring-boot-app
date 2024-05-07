package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.*;
import org.minnnisu.togetherdelivery.dto.chat.*;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

//    TODO: 최적화
    public ChatRoomCreateResponseDto createRoom(ChatRoomCreateRequestDto chatRoomCreateRequestDto, User user) {
        if(user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Post post = postRepository.findById(chatRoomCreateRequestDto.getPostId()).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));

        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByPost(post);
        if(chatRoomOptional.isPresent()) {
            throw new CustomErrorException(ErrorCode.AlreadyExistChatRoomError);
        }

        List<String> invitedMembers = chatRoomCreateRequestDto.getMembers();
        List<User> invitedUsers = new ArrayList<>();
        for (String member : invitedMembers) {
            User foundUser = userRepository.findByNickname(member).orElseThrow(() -> new CustomErrorException(ErrorCode.UserNotFoundError));
            invitedUsers.add(foundUser);
        }

        ChatRoom newChatRoom = chatRoomRepository.save(ChatRoom.of(post));

        List<ChatRoomMember> chatRoomMembers = new ArrayList<>();
        for (User invitedUser : invitedUsers) {
            chatRoomMembers.add(chatRoomMemberRepository.save(ChatRoomMember.of(newChatRoom, invitedUser)));
        }

        return ChatRoomCreateResponseDto.fromEntity(chatRoomMembers);
    }

    public ChatRoomListResponseDto getChatRoomList(User user) {
        if(user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findAllByUser(user);
        return ChatRoomListResponseDto.fromEntity(chatRoomMembers);
    }

    public ChatRoomInviteResponseDto inviteMember(ChatRoomInviteRequestDto chatRoomInviteRequestDto, User user) {
        if(user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomInviteRequestDto.getChatRoomId()).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));

        List<String> invitedMembers = chatRoomInviteRequestDto.getInvitedMembers();
        List<ChatRoomMember> chatRoomMembers = new ArrayList<>();
        for (String member : invitedMembers) {
            User foundUser = userRepository.findByNickname(member).orElseThrow(() -> new CustomErrorException(ErrorCode.UserNotFoundError));
            Optional<ChatRoomMember> chatRoomMember = chatRoomMemberRepository.findAllByChatRoomAndUser(chatRoom, foundUser);
            if(chatRoomMember.isPresent()){
                throw new CustomErrorException(ErrorCode.AlreadyExistChatRoomMemberError);
            }
            chatRoomMembers.add(chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, foundUser)));
        }

        return ChatRoomInviteResponseDto.fromEntity(chatRoomMembers);
    }


    public ChatRoomExitResponseDto exitChatRoom(ChatRoomExitRequestDto chatRoomExitRequestDto, User user) {
        if(user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomExitRequestDto.getChatRoomId())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchMemberInChatRoomError));
        chatMessageRepository.deleteAllByChatRoomMember(chatRoomMember);


        ChatRoomExitResponseDto chatRoomExitResponseDto = ChatRoomExitResponseDto.fromEntity(chatRoomMember);
        chatRoomMemberRepository.delete(chatRoomMember);

        return chatRoomExitResponseDto;
    }
}
