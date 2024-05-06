package org.minnnisu.togetherdelivery.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.constant.TokenType;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.exception.CustomMessageDeliveryException;
import org.minnnisu.togetherdelivery.provider.JwtTokenProvider;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationInterceptor implements ChannelInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (!StompCommand.CONNECT.equals(accessor.getCommand())) {
           return message;
        }

        String accessToken = jwtTokenProvider.resolveToken(accessor.getFirstNativeHeader("Authorization"));
        if (accessToken == null) {
            throw new CustomMessageDeliveryException(ErrorCode.NotValidAccessTokenError);
        }

        try {
            jwtTokenProvider.validateToken(TokenType.ACCESS_TOKEN, accessToken);
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.InternalServerError;
            if (e instanceof CustomErrorException) {
                errorCode = ((CustomErrorException) e).getErrorCode();
            }

            throw new CustomMessageDeliveryException(errorCode);
        }
        return message;
    }
}