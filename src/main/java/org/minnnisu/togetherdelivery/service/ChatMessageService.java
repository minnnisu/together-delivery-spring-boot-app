package org.minnnisu.togetherdelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.stomp.chatMessage.chatMessageDelete.ChatMessageDeleteDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageDeleteDto deleteChatMessage(Long chatMessageId, User user) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserPermissionDeniedError);
        }

        ChatMessage deleteTargetchatMessage = chatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatMessageError));

        User creator = deleteTargetchatMessage.getSender().getUser();
        if(!Objects.equals(creator.getId(), user.getId())){
            throw new CustomErrorException(ErrorCode.NotTheSenderOfChatMessage);
        }

        ChatMessageDeleteDto chatMessageDeleteDto = ChatMessageDeleteDto.fromEntity(deleteTargetchatMessage);

        chatMessageRepository.delete(deleteTargetchatMessage);

        return chatMessageDeleteDto;

    }
}
