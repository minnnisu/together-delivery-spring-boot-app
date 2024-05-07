package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.ChatRoomInviteRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.ChatRoomInviteResponseDto;
import org.minnnisu.togetherdelivery.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/room/invite")
public class ChatRoomInviteController {
    private final ChatRoomService chatRoomService;

    @PostMapping()
    public ResponseEntity<ChatRoomInviteResponseDto> inviteMember(
            @Valid @RequestBody ChatRoomInviteRequestDto chatRoomInviteRequestDto,
            @AuthenticationPrincipal User user
    ){
        ChatRoomInviteResponseDto chatRoomInviteResponseDto = chatRoomService.inviteMember(chatRoomInviteRequestDto, user);
        return new ResponseEntity<>(chatRoomInviteResponseDto, HttpStatus.CREATED);
    }
}
