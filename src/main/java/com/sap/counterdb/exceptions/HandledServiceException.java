package com.sap.counterdb.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandledServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private ErrorSource errorSource;

    public HandledServiceException(final ErrorSource errorSource, final ErrorCode errorCode, final Object... args) {
        super(errorCode.getDefaultMessage(args));
        this.errorCode = errorCode;
        this.errorSource = errorSource;
    }

    public HandledServiceException(final ErrorSource errorSource, final Exception ex, final ErrorCode errorCode) {
        super(ex);
        this.errorCode = errorCode;
        this.errorSource = errorSource;
    }

}
