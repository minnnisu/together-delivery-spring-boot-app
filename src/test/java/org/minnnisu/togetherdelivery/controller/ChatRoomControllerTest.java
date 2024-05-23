package org.minnnisu.togetherdelivery.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.dto.chat.*;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(ChatRoomController.class)
class ChatRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatRoomService chatRoomService;


    @Nested
    @DisplayName("채팅방 리스트 요청")
    class WhenRequestingChatRoomList {
        @Test
        @WithMockUser()
        @DisplayName("채팅방 리스트를 응답한다.")
        void thenResponseChatRoomList() throws Exception {
            // given
            given(chatRoomService.getChatRoomList(any()))
                    .willReturn(ChatRoomListResponseDto.of(
                            List.of(
                                    ChatRoomDto.of(1L, 1L, LocalDateTime.of(2024, 5, 1, 1, 1, 1)),
                                    ChatRoomDto.of(2L, 2L, LocalDateTime.of(2024, 5, 1, 1, 1, 1))
                            )
                    ));

            // when & then
            mockMvc.perform(get("/api/chat/room"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.chatRooms.length()").value(2))
                    .andExpect(jsonPath("$.chatRooms[0].chatRoomId").value(1L))
                    .andExpect(jsonPath("$.chatRooms[0].postId").value(1L))
                    .andExpect(jsonPath("$.chatRooms[0].createdAt").value(LocalDateTime.of(2024, 5, 1, 1, 1, 1).toString()))
                    .andExpect(jsonPath("$.chatRooms[1].chatRoomId").value(2L))
                    .andExpect(jsonPath("$.chatRooms[1].postId").value(2L))
                    .andExpect(jsonPath("$.chatRooms[1].createdAt").value(LocalDateTime.of(2024, 5, 1, 1, 1, 1).toString()))
            ;
        }

        @Test
        @WithMockUser
        @DisplayName("UserPermissionDeniedError를 발생시킨다.")
        void thenThrowUserPermissionDeniedError() throws Exception {
            ErrorCode userPermissionDeniedError = ErrorCode.UserPermissionDeniedError;

            // given
            given(chatRoomService.getChatRoomList(any()))
                    .willThrow(new CustomErrorException(userPermissionDeniedError));

            // when & then
            mockMvc.perform(get("/api/chat/room"))
                    .andExpect(status().is(userPermissionDeniedError.getHttpStatus().value()))
                    .andExpect(jsonPath("$.errorCode").value(userPermissionDeniedError.name()))
                    .andExpect(jsonPath("$.message").value(userPermissionDeniedError.getMessage()))
            ;
        }

    }

    @Nested
    @DisplayName("채팅방 초대 요청")
    class WhenInvitingChatRoom{
        @Test
        @WithMockUser
        @DisplayName("채팅방에 초대한다.")
        void thenInviteChatRoom() throws Exception{
            ChatRoomInviteRequestDto chatRoomInviteRequestDto = new ChatRoomInviteRequestDto(1L, "user1");

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(chatRoomInviteRequestDto);

            // given
            given(chatRoomService.inviteMember(any(), any()))
                    .willReturn(new ChatRoomInviteResponseDto(
                                    1L,
                                    "minnnisu",
                                    LocalDateTime.of(2024, 5, 1, 1, 1, 1)));


            // when & then
            mockMvc.perform(
                            post("/api/chat/room/invite")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody)
                                    .with(csrf()))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.chatRoomId").value(1L))
                    .andExpect(jsonPath("$.invitedMember").value("minnnisu"))
                    .andExpect(jsonPath("$.createdAt").value(
                            LocalDateTime.of(2024, 5, 1, 1, 1, 1).toString()
                    ))
            ;
        }

        @Test
        @WithMockUser
        @DisplayName("UserPermissionDeniedError를 발생시킨다.")
        void thenThrowUserPermissionDeniedError() throws Exception{
            ErrorCode userPermissionDeniedError = ErrorCode.UserPermissionDeniedError;

            testCustomException(userPermissionDeniedError, mockMvc);
        }

        @Test
        @WithMockUser
        @DisplayName("NoSuchChatRoomError를 발생시킨다.")
        void thenThrowNoSuchChatRoomError() throws Exception{
            ErrorCode userPermissionDeniedError = ErrorCode.NoSuchChatRoomError;

            testCustomException(userPermissionDeniedError, mockMvc);
        }

        @Test
        @WithMockUser
        @DisplayName("NoSuchMemberInChatRoomError를 발생시킨다.")
        void thenThrowNoSuchMemberInChatRoomError() throws Exception {
            ErrorCode userPermissionDeniedError = ErrorCode.NoSuchMemberInChatRoomError;

            testCustomException(userPermissionDeniedError, mockMvc);
        }

        @Test
        @WithMockUser
        @DisplayName("ChatInvitePermissionDeniedError를 발생시킨다.")
        void thenThrowChatInvitePermissionDeniedError() throws Exception {
            ErrorCode userPermissionDeniedError = ErrorCode.ChatInvitePermissionDeniedError;

            testCustomException(userPermissionDeniedError, mockMvc);
        }


        @Test
        @WithMockUser
        @DisplayName("AlreadyExistChatRoomMemberError를 발생시킨다.")
        void thenThrowAlreadyExistChatRoomMemberError() throws Exception{
            ErrorCode userPermissionDeniedError = ErrorCode.AlreadyExistChatRoomMemberError;

            testCustomException(userPermissionDeniedError, mockMvc);
        }


        private void testCustomException(ErrorCode errorCode, MockMvc mockMvc) throws Exception {
            ChatRoomInviteRequestDto chatRoomInviteRequestDto = new ChatRoomInviteRequestDto(1L, "user1");

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(chatRoomInviteRequestDto);


            // given
            given(chatRoomService.inviteMember(any(), any()))
                    .willThrow(new CustomErrorException(errorCode));

            // when & then
            mockMvc.perform(
                            post("/api/chat/room/invite")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody)
                                    .with(csrf()))
                    .andExpect(status().is(errorCode.getHttpStatus().value()))
                    .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                    .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
            ;
        }
    }

    @Nested
    @DisplayName("채팅방 탈퇴 요청")
    class WhenExitingChatRoom {
        @Test
        @WithMockUser
        @DisplayName("채팅방을 탈퇴한다.")
        void thenExitChatRoom() throws Exception {
            ChatRoomExitRequestDto chatRoomExitRequestDto = new ChatRoomExitRequestDto(1L);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(chatRoomExitRequestDto);

            // given
            given(chatRoomService.exitChatRoom(any(), any()))
                    .willReturn(new ChatRoomExitResponseDto(
                            1L,
                            "minnnisu",
                            LocalDateTime.of(2024, 5, 1, 1, 1, 1)));


            // when & then
            mockMvc.perform(
                            delete("/api/chat/room/exit")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody)
                                    .with(csrf()))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.chatRoomId").value(1L))
                    .andExpect(jsonPath("$.deletedMember").value("minnnisu"))
                    .andExpect(jsonPath("$.deletedAt").value(
                            LocalDateTime.of(2024, 5, 1, 1, 1, 1).toString()
                    ))
            ;
        }

        @Test
        @WithMockUser
        @DisplayName("UserPermissionDeniedError를 발생시킨다.")
        void thenThrowUserPermissionDeniedError() throws Exception{
            ErrorCode userPermissionDeniedError = ErrorCode.UserPermissionDeniedError;

            testCustomException(userPermissionDeniedError, mockMvc);
        }

        @Test
        @WithMockUser
        @DisplayName("NoSuchChatRoomError를 발생시킨다.")
        void thenThrowNoSuchChatRoomError() throws Exception{
            ErrorCode userPermissionDeniedError = ErrorCode.NoSuchChatRoomError;

            testCustomException(userPermissionDeniedError, mockMvc);
        }

        @Test
        @WithMockUser
        @DisplayName("NoSuchMemberInChatRoomError를 발생시킨다.")
        void thenThrowNoSuchMemberInChatRoomError() throws Exception{
            ErrorCode userPermissionDeniedError = ErrorCode.NoSuchMemberInChatRoomError;

            testCustomException(userPermissionDeniedError, mockMvc);
        }

        private void testCustomException(ErrorCode errorCode, MockMvc mockMvc) throws Exception {
            ChatRoomExitRequestDto chatRoomExitRequestDto = new ChatRoomExitRequestDto(1L);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(chatRoomExitRequestDto);

            // given
            given(chatRoomService.exitChatRoom(any(), any()))
                    .willThrow(new CustomErrorException(errorCode));


            // when & then
            mockMvc.perform(
                            delete("/api/chat/room/exit")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody)
                                    .with(csrf()))
                    .andExpect(status().is(errorCode.getHttpStatus().value()))
                    .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                    .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
            ;
        }
    }
}