package org.minnnisu.togetherdelivery.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NotValidRequestErrorResponseDto {
    private String errorCode;
    private String message;
    private List<ErrorDescription> errorDescriptions;
    
    public static NotValidRequestErrorResponseDto of(List<ErrorDescription> errorDescription){
        return NotValidRequestErrorResponseDto.builder()
                .errorCode(ErrorCode.NotValidRequestError.name())
                .message(ErrorCode.NotValidRequestError.getMessage())
                .errorDescriptions(errorDescription)
                .build();
    }

    public static NotValidRequestErrorResponseDto of(BindException e, List<ErrorDescription> errorDescription){
        return NotValidRequestErrorResponseDto.builder()
                .errorCode(ErrorCode.NotValidRequestError.name())
                .message(ErrorCode.NotValidRequestError.getMessage())
                .errorDescriptions(errorDescription)
                .build();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ErrorDescription{
        private String message;

        public static ErrorDescription of(String message){
            return ErrorDescription.builder()
                    .message(message)
                    .build();
        }


        public static ErrorDescription of(FieldError error){
            return ErrorDescription.builder()
                    .message(error.getDefaultMessage())
                    .build();
        }
    }


}
