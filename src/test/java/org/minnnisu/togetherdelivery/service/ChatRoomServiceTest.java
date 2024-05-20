package org.minnnisu.togetherdelivery.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.*;
import org.minnnisu.togetherdelivery.dto.chat.*;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private ChatRoomMemberRepository chatRoomMemberRepository;
    @Mock
    private ChatMessageRepository chatMessageRepository;

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


            verify(chatRoomMemberRepository, atLeastOnce()).findAllByUser(user);
        }

        @Test
        @DisplayName("UserPermissionDeniedError를 반환한다.")
        void ThenThrowUserPermissionDeniedError(){
            ErrorCode userPermissionDeniedError = ErrorCode.UserPermissionDeniedError;

            Throwable thrown = catchThrowable(() -> chatRoomService.getChatRoomList(null));

            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(userPermissionDeniedError.getMessage());
        }
    }

    @Nested
    @DisplayName("채팅방 생성")
    class WhenCreateChatRoom{
        @Test
        @DisplayName("채팅방을 생성한다.")
        void ThenCreateChatRoom(){
            // given
            Optional<Post> postOptional = Optional.of(createPost());
            Optional<ChatRoom> chatRoomOptional = Optional.empty();
            ChatRoom chatRoom2 = createChatRoom2();
            ChatRoomMember chatRoomCreator = createChatRoomMember(true);

            given(postRepository.findById(any())).willReturn(postOptional);
            given(chatRoomRepository.findByPost(any())).willReturn(chatRoomOptional);
            given(chatRoomRepository.save(any())).willReturn(chatRoom2);
            given(chatRoomMemberRepository.save(any())).willReturn(chatRoomCreator);

            // when
            ChatRoomCreateRequestDto chatRoomCreateRequestDto = new ChatRoomCreateRequestDto(1L);
            User user = createUser();

            ChatRoomCreateResponseDto result =
                    chatRoomService.createRoom(chatRoomCreateRequestDto, user);

            // then
            assertThat(result.getChatRoomId())
                    .isEqualTo(chatRoomCreator.getChatRoom().getId());
            assertThat(result.getPostId())
                    .isEqualTo(chatRoomCreator.getChatRoom().getPost().getId());
            assertThat(result.getCreator())
                    .isEqualTo(chatRoomCreator.getUser().getUsername());
            assertThat(result.getCreatedAt())
                    .isEqualTo(chatRoomCreator.getCreatedAt());

            verify(postRepository, atLeastOnce()).findById(any());
            verify(chatRoomRepository, atLeastOnce()).findByPost(any());
            verify(chatRoomRepository, atLeastOnce()).save(any());
            verify(chatRoomMemberRepository, atLeastOnce()).save(any());
            verify(chatMessageRepository, atLeastOnce()).save(any());
        }

        @Test
        @DisplayName("UserPermissionDeniedError를 반환한다.")
        void ThenThrowUserPermissionDeniedError(){
            ErrorCode userPermissionDeniedError = ErrorCode.UserPermissionDeniedError;

            // when
            ChatRoomCreateRequestDto chatRoomCreateRequestDto = new ChatRoomCreateRequestDto(1L);
            Throwable thrown = catchThrowable(() -> chatRoomService.createRoom(chatRoomCreateRequestDto, null));

            // then
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(userPermissionDeniedError.getMessage());
        }

        @Test
        @DisplayName("NoSuchPostError를 반환한다.")
        void ThenThrowNoSuchPostError(){
            ErrorCode noSuchPostError = ErrorCode.NoSuchPostError;

            // given
            User user = createUser();
            Optional<Post> postOptional = Optional.empty();
            given(postRepository.findById(any())).willReturn(postOptional);


            // when
            ChatRoomCreateRequestDto chatRoomCreateRequestDto = new ChatRoomCreateRequestDto(1L);
            Throwable thrown = catchThrowable(() -> chatRoomService.createRoom(chatRoomCreateRequestDto, user));

            // then
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(noSuchPostError.getMessage());
        }

        @Test
        @DisplayName("AlreadyExistChatRoomError를 반환한다.")
        void ThenThrowAlreadyExistChatRoomError(){
            ErrorCode alreadyExistChatRoomError = ErrorCode.AlreadyExistChatRoomError;

            // given
            User user = createUser();
            Optional<Post> postOptional = Optional.of(createPost());
            Optional<ChatRoom> chatRoomOptional = Optional.of(createChatRoom());

            given(postRepository.findById(any())).willReturn(postOptional);
            given(chatRoomRepository.findByPost(any())).willReturn(chatRoomOptional);


            // when
            ChatRoomCreateRequestDto chatRoomCreateRequestDto = new ChatRoomCreateRequestDto(1L);
            Throwable thrown = catchThrowable(() -> chatRoomService.createRoom(chatRoomCreateRequestDto, user));

            // then
            assertThat(thrown)
                    .isInstanceOf(CustomErrorException.class)
                    .hasMessage(alreadyExistChatRoomError.getMessage());
        }
    }
}