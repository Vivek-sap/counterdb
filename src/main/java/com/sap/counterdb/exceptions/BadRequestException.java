package com.sap.counterdb.exceptions;

public class BadRequestException extends HandledServiceException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(final ErrorSource errorSource, final ErrorCode errorCode, final Object... args) {
        super(errorSource, errorCode, args);
    }

}
