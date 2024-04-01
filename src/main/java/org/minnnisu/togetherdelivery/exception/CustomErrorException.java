package org.minnnisu.togetherdelivery.exception;

import lombok.Getter;
import org.minnnisu.togetherdelivery.constant.ErrorCode;

@Getter
public class CustomErrorException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomErrorException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
