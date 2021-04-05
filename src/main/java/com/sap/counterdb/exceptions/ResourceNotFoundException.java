package com.sap.counterdb.exceptions;


public class ResourceNotFoundException extends HandledServiceException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(final ErrorSource errorSource, final ErrorCode errorCode, final Object... args) {
        super(errorSource, errorCode, args);
    }
}
