package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.ChatRoomCreateRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.ChatRoomCreateResponseDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.*;
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
}
