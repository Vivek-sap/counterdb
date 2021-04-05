package com.sap.counterdb.exceptions;


public enum ErrorCode {

    INVALID_DATA("3", "%s"), TECHNICAL_PROBLEM("75", "%s");


    private final String defaultMessage;
    private final String errorCode;

    ErrorCode(String errorCode, String defaultMessage) {
        this.defaultMessage = defaultMessage;
        this.errorCode = errorCode;
    }

    public String code() {
        return name();
    }

    public String getDefaultMessage(Object... args) {
        return String.format(defaultMessage, args);
    }

    public String getErrorCode() {
        return errorCode;
    }

}
