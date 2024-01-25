package com.objects.marketbridge.global.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomLogicException extends RuntimeException {

    private ErrorCode errorCode;
    private String errorCodeStr;
    private String message;

    public CustomLogicException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public CustomLogicException(String message, String errorCodeStr) {
        super(message);
        this.errorCodeStr = errorCodeStr;
    }

    public CustomLogicException(String message) {
        super(message);
    }
}
