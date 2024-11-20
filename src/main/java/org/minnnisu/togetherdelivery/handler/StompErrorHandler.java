package org.minnnisu.togetherdelivery.handler;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.exception.CustomMessageDeliveryException;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StompErrorHandler extends StompSubProtocolErrorHandler {

    /**
     * 클라이언트 메시지 처리 중에 발생한 오류를 처리
     *
     * @param clientMessage 클라이언트 메시지
     * @param ex 발생한 예외
     * @return 오류 메시지를 포함한 Message 객체
     */
    @Override
    public Message<byte[]> handleClientMessageProcessingError(
            Message<byte[]> clientMessage,
            Throwable ex) {

        if (ex instanceof CustomMessageDeliveryException) {
            return errorMessage(((CustomMessageDeliveryException) ex).getErrorCode().name());
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    /**
     * 오류 메시지를 포함한 Message 객체를 생성
     *
     * @param errorMessage 오류 메시지
     * @return 오류 메시지를 포함한 Message 객체
     */
    private Message<byte[]> errorMessage(String errorMessage) {

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(errorMessage.getBytes(StandardCharsets.UTF_8),
                accessor.getMessageHeaders());
    }
}