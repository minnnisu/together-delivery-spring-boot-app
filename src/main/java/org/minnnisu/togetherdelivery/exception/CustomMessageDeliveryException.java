package org.minnnisu.togetherdelivery.exception;

import lombok.Getter;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.springframework.messaging.MessageDeliveryException;

@Getter
public class CustomMessageDeliveryException extends MessageDeliveryException {
    final private ErrorCode errorCode;

    public CustomMessageDeliveryException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
