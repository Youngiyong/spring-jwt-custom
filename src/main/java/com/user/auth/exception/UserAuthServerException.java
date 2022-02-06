package com.user.auth.exception;

public class UserAuthServerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    public UserAuthServerException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserAuthServerException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public UserAuthServerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public UserAuthServerException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }


    public ErrorCode getErrorCode() {
        return errorCode ;
    }
}