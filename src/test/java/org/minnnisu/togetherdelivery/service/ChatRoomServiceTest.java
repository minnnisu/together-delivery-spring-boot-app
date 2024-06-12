package org.minnnisu.togetherdelivery.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.*;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse.stomp.StompChatMessageEnterResponseDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitResponseDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomInvite.ChatRoomInviteDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomInvite.ChatRoomInviteRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomList.ChatRoomListResponseDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;
import java.util.Optional;

import static org.minnnisu.togetherdelivery.common.DummyData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private ChatRoomMemberRepository chatRoomMemberRepository;
    @Mock
    private ChatMessageRepository chatMessageRepository;
    @Mock
    private StompChatService stompChatService;

    @InjectMocks
    private ChatRoomService chatRoomService;

    @Nested
    @DisplayName("채팅방 리스트 요청")
    class WhenRequestingChatRoomList {
        @Test
        @DisplayName("채팅방 리스트를 반환한다.")
        void ThenReturnChatRoomList(){
            User user = createUser();

            ChatRoomMember chatRoomMember = createChatRoomMember(true);

            given(chatRoomMemberRepository.findAllByUser(user))
                    .willReturn(List.of(
                            chatRoomMember
                    ));

            ChatRoomListResponseDto result = chatRoomService.getChatRoomList(user);

            Assertions.assertThat(result.getChatRooms().size()).isEqualTo(1);
            Assertions.assertThat(result.getChatRooms().get(0).getChatRoomId())
                    .isEqualTo(chatRoomMember.getChatRoom().getId());
            Assertions.assertThat(result.getChatRooms().get(0).getPostId())
                    .isEqualTo(chatRoomMember.getChatRoom().getPost().getId());
            Assertions.assertThat(result.getChatRooms().get(0).getCreatedAt())
                    .isEqualTo(chatRoomMember.getChatRoom().getPost().getCreatedAt());


            verify(chatRoomMemberRepository, times(1)).findAllByUser(user);
        }

        @Test
        @DisplayName("UserPermissionDeniedError가 발생한다.")
        void ThenThrowUserPermissionDeniedError(){
            ErrorCode userPermissionDeniedError = ErrorCode.UserPermissionDeniedError;

            Throwable thrown = catchThrowable(() -> chatRoomService.getChatRoomList(null));

            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(userPermissionDeniedError.getMessage());
        }
    }

    @Nested
    @DisplayName("채팅방 초대")
    class WhenInviteChatRoom{
        @Test
        @DisplayName("체팅방에 초대한다")
        void ThenInviteChatRoom() {
            // given
            User creator = createUser();
            ChatRoomInviteRequestDto chatRoomInviteRequestDto = new ChatRoomInviteRequestDto(1L, creator.getNickname());

            Optional<ChatRoom> chatRoomOptional = Optional.of(createChatRoom());
            Optional<ChatRoomMember> chatRoomCreator = Optional.of(createChatRoomMember(true));
            User invitedMember = createUser2();
            ChatRoomMember newChatRoomMember = createChatRoomMember2(false);
            ChatMessage chatMessage = createChatMessage(ChatMessageType.ENTER);

            StompChatMessageEnterResponseDto stompChatMessageEnterResponseDto = StompChatMessageEnterResponseDto.fromEntity(chatMessage, newChatRoomMember);
            ChatMessageDto chatMessageDto = ChatMessageDto.of("/topic/chat/room/1", stompChatMessageEnterResponseDto);

            given(chatRoomRepository.findById(any())).willReturn(chatRoomOptional);
            given(chatRoomMemberRepository.findByChatRoomAndUser(any(), eq(creator))).willReturn(chatRoomCreator);
            given(userRepository.findByNickname(any())).willReturn(Optional.of(invitedMember));
            given(chatRoomMemberRepository.findByChatRoomAndUser(any(), eq(invitedMember))).willReturn(Optional.empty());
            given(chatRoomMemberRepository.save(any())).willReturn(newChatRoomMember);
            given(stompChatService.sendInvitationMessage(any(), any(), any())).willReturn(chatMessageDto);

            // when
            ChatRoomInviteDto result = chatRoomService.inviteMember(chatRoomInviteRequestDto, creator);
            assertThat(result.getChatRoomId())
                    .isEqualTo(newChatRoomMember.getChatRoom().getId());
            assertThat(result.getInvitedMember())
                    .isEqualTo(newChatRoomMember.getUser().getNickname());
            assertThat(result.getCreatedAt())
                    .isEqualTo(newChatRoomMember.getCreatedAt());
            assertThat(result.getChatMessage()).isSameAs(chatMessageDto);

            verify(chatRoomRepository, times(1)).findById(any());
            verify(chatRoomMemberRepository, times(1)).findByChatRoomAndUser(any(), eq(creator));
            verify(userRepository, times(1)).findByNickname(any());
            verify(chatRoomMemberRepository, times(1)).findByChatRoomAndUser(any(), eq(invitedMember));
            verify(chatRoomMemberRepository, times(1)).save(any());
            verify(stompChatService, times(1)).sendInvitationMessage(any(), any(), any());
        }

        @Test
        @DisplayName("UserPermissionDeniedError가 발생한다.")
        void ThenUserPermissionDeniedError() {
            // given
            ErrorCode userPermissionDeniedError = ErrorCode.UserPermissionDeniedError;

            User invitedMember = createUser2();
            ChatRoomInviteRequestDto chatRoomInviteRequestDto = new ChatRoomInviteRequestDto(1L, invitedMember.getNickname());

            // when
            Throwable thrown = catchThrowable(() -> chatRoomService.inviteMember(chatRoomInviteRequestDto, null));

            // then
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(userPermissionDeniedError.getMessage());
        }

        @Test
        @DisplayName("NoSuchChatRoomError가 발생한다.")
        void ThenNoSuchChatRoomError() {
            // given
            User invitedMember = createUser2();
            User creator = createUser();
            ChatRoomInviteRequestDto chatRoomInviteRequestDto = new ChatRoomInviteRequestDto(1L, invitedMember.getNickname());

            given(chatRoomRepository.findById(any())).willReturn(Optional.empty());

            // when
            Throwable thrown = catchThrowable(() -> chatRoomService.inviteMember(chatRoomInviteRequestDto, creator));

            // then
            ErrorCode noSuchChatRoomError = ErrorCode.NoSuchChatRoomError;
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(noSuchChatRoomError.getMessage());

            verify(chatRoomRepository, times(1)).findById(any());
        }

        @Test
        @DisplayName("NoSuchMemberInChatRoomError가 발생한다.")
        void ThenNoSuchMemberInChatRoomError() {
            // given
            User invitedMember = createUser2();
            User creator = createUser();
            ChatRoomInviteRequestDto chatRoomInviteRequestDto = new ChatRoomInviteRequestDto(1L, invitedMember.getNickname());
            Optional<ChatRoom> chatRoomOptional = Optional.of(createChatRoom());


            given(chatRoomRepository.findById(any())).willReturn(chatRoomOptional);
            given(chatRoomMemberRepository.findByChatRoomAndUser(any(), eq(creator))).willReturn(Optional.empty());

            // when
            Throwable thrown = catchThrowable(() -> chatRoomService.inviteMember(chatRoomInviteRequestDto, creator));

            // then
            ErrorCode noSuchMemberInChatRoomError = ErrorCode.NoSuchMemberInChatRoomError;
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(noSuchMemberInChatRoomError.getMessage());

            verify(chatRoomRepository, times(1)).findById(any());
            verify(chatRoomMemberRepository, times(1)).findByChatRoomAndUser(any(), any());
        }

        @Test
        @DisplayName("ChatInvitePermissionDeniedError가 발생한다.")
        void ThenChatInvitePermissionDeniedError() {
            // given
            User invitedMember = createUser2();
            User creator = createUser();
            ChatRoomInviteRequestDto chatRoomInviteRequestDto = new ChatRoomInviteRequestDto(1L, invitedMember.getNickname());
            Optional<ChatRoom> chatRoomOptional = Optional.of(createChatRoom());
            Optional<ChatRoomMember> noChatRoomCreator = Optional.of(createChatRoomMember(false));


            given(chatRoomRepository.findById(any())).willReturn(chatRoomOptional);
            given(chatRoomMemberRepository.findByChatRoomAndUser(any(), eq(creator))).willReturn(noChatRoomCreator);

            // when
            Throwable thrown = catchThrowable(() -> chatRoomService.inviteMember(chatRoomInviteRequestDto, creator));

            // then
            ErrorCode chatInvitePermissionDeniedError = ErrorCode.ChatInvitePermissionDeniedError;
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(chatInvitePermissionDeniedError.getMessage());

            verify(chatRoomRepository, times(1)).findById(any());
            verify(chatRoomMemberRepository, times(1)).findByChatRoomAndUser(any(), any());
        }

        @Test
        @DisplayName("AlreadyExistChatRoomMemberError가 발생한다.")
        void ThenAlreadyExistChatRoomMemberError() {
            // given
            User invitedMember = createUser2();
            User creator = createUser();
            ChatRoomInviteRequestDto chatRoomInviteRequestDto = new ChatRoomInviteRequestDto(1L, invitedMember.getNickname());
            Optional<ChatRoom> chatRoomOptional = Optional.of(createChatRoom());
            Optional<ChatRoomMember> noChatRoomCreator = Optional.of(createChatRoomMember(true));
            Optional<ChatRoomMember> alreadyExistChatRoomMember = Optional.of(createChatRoomMember2(false));


            given(chatRoomRepository.findById(any())).willReturn(chatRoomOptional);
            given(chatRoomMemberRepository.findByChatRoomAndUser(any(), eq(creator))).willReturn(noChatRoomCreator);
            given(userRepository.findByNickname(any())).willReturn(Optional.of(invitedMember));
            given(chatRoomMemberRepository.findByChatRoomAndUser(any(), eq(invitedMember))).willReturn(alreadyExistChatRoomMember);

            // when
            Throwable thrown = catchThrowable(() -> chatRoomService.inviteMember(chatRoomInviteRequestDto, creator));

            // then
            ErrorCode alreadyExistChatRoomMemberError = ErrorCode.AlreadyExistChatRoomMemberError;
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(alreadyExistChatRoomMemberError.getMessage());

            verify(chatRoomRepository, times(1)).findById(any());
            verify(chatRoomMemberRepository, times(1)).findByChatRoomAndUser(any(), eq(creator));
            verify(userRepository, times(1)).findByNickname(any());
            verify(chatRoomMemberRepository, times(1)).findByChatRoomAndUser(any(), eq(invitedMember));
        }
    }

    @Nested
    @DisplayName("채팅방 탈퇴")
    class WhenExitChatRoom {
        @Test
        @DisplayName("채팅방을 탈퇴한다.")
        void ThenExitChatRoom(){
            // given
            User exitedUser = createUser();
            ChatRoomExitRequestDto chatRoomExitRequestDto = new ChatRoomExitRequestDto(1L);
            Optional<ChatRoom> chatRoomOptional = Optional.of(createChatRoom());
            Optional<ChatRoomMember> chatRoomMemberOptional = Optional.of(createChatRoomMember(false));
            List<ChatMessage> chatMessages = List.of(
                    ChatMessage.of(chatRoomMemberOptional.get(), "메시지1", ChatMessageType.TALK),
                    ChatMessage.of(chatRoomMemberOptional.get(), "메시지2", ChatMessageType.TALK),
                    ChatMessage.of(chatRoomMemberOptional.get(), "메시지3", ChatMessageType.TALK)
            );

            given(chatRoomRepository.findById(any())).willReturn(chatRoomOptional);
            given(chatRoomMemberRepository.findByChatRoomAndUser(any(), any())).willReturn(chatRoomMemberOptional);
            given(chatMessageRepository.findAllBySender(any())).willReturn(chatMessages);

            // when
            ChatRoomExitResponseDto result = chatRoomService.exitChatRoom(chatRoomExitRequestDto, exitedUser);

            // then
            assertThat(result.getChatRoomId())
                    .isEqualTo(chatRoomMemberOptional.get().getChatRoom().getId());
            assertThat(result.getDeletedMember())
                    .isEqualTo(chatRoomMemberOptional.get().getUser().getUsername());

            verify(chatRoomRepository, times(1)).findById(any());
            verify(chatRoomMemberRepository, times(1)).findByChatRoomAndUser(any(), any());
            verify(chatMessageRepository, times(1)).findAllBySender(any());
            verify(chatRoomMemberRepository, times(1)).delete(any());
        }

        @Test
        @DisplayName("UserPermissionDeniedError가 발생한다.")
        void ThenThrowUserPermissionDeniedError(){
            // given
            ChatRoomExitRequestDto chatRoomExitRequestDto = new ChatRoomExitRequestDto(1L);

            // when
            Throwable thrown = catchThrowable(() -> chatRoomService.exitChatRoom(chatRoomExitRequestDto, null));

            // then
            ErrorCode userPermissionDeniedError = ErrorCode.UserPermissionDeniedError;
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(userPermissionDeniedError.getMessage());

        }

        @Test
        @DisplayName("NoSuchChatRoomError가 발생한다.")
        void ThenThrowNoSuchChatRoomError(){
            // given
            User exitedUser = createUser();
            ChatRoomExitRequestDto chatRoomExitRequestDto = new ChatRoomExitRequestDto(1L);
            given(chatRoomRepository.findById(any())).willReturn(Optional.empty());

            // when
            Throwable thrown = catchThrowable(() -> chatRoomService.exitChatRoom(chatRoomExitRequestDto, exitedUser));


            // then
            ErrorCode noSuchChatRoomError = ErrorCode.NoSuchChatRoomError;
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(noSuchChatRoomError.getMessage());

            verify(chatRoomRepository, times(1)).findById(any());
        }

        @Test
        @DisplayName("NoSuchMemberInChatRoomError가 발생한다.")
        void ThenThrowNoSuchMemberInChatRoomError(){
            // given
            User exitedUser = createUser();
            ChatRoomExitRequestDto chatRoomExitRequestDto = new ChatRoomExitRequestDto(1L);

            Optional<ChatRoom> chatRoomOptional = Optional.of(createChatRoom());
            given(chatRoomRepository.findById(any())).willReturn(chatRoomOptional);
            given(chatRoomMemberRepository.findByChatRoomAndUser(any(), any())).willReturn(Optional.empty());


            // when
            Throwable thrown = catchThrowable(() -> chatRoomService.exitChatRoom(chatRoomExitRequestDto, exitedUser));


            // then
            ErrorCode noSuchMemberInChatRoomError = ErrorCode.NoSuchMemberInChatRoomError;
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(noSuchMemberInChatRoomError.getMessage());

            verify(chatRoomRepository, times(1)).findById(any());
            verify(chatRoomMemberRepository, times(1)).findByChatRoomAndUser(any(), any());
        }
    }
}