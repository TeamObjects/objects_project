package com.objects.marketbridge.domain.payment.exception;

public class RefundRejectException extends RuntimeException {

    public RefundRejectException() {
        super();
    }

    public RefundRejectException(String message) {
        super(message);
    }

    public RefundRejectException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefundRejectException(Throwable cause) {
        super(cause);
    }

    protected RefundRejectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
