package com.rayzhou.springboottemplate.common;

public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "input parameter error"),
    NOT_LOGIN_ERROR(40100, "not login"),
    NO_AUTH_ERROR(40101, "no authorization"),
    NOT_FOUND_ERROR(40400, "data not found"),
    FORBIDDEN_ERROR(40300, "forbidden"),
    SYSTEM_ERROR(50000, "internal error"),
    OPERATION_ERROR(50001, "operation error");

    /**
     * status
     */
    private final int code;

    /**
     * info
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}