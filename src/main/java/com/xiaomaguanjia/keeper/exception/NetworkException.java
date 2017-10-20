package com.xiaomaguanjia.keeper.exception;

public class NetworkException extends RuntimeException {
    public NetworkException(Integer code, String message) {
        super(String.format("[%s]%s", code, message));
        this.code = code;
    }

    public NetworkException(String message, Throwable e) {
        super(message, e);
    }

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}