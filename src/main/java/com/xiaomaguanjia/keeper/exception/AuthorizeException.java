package com.xiaomaguanjia.keeper.exception;

import org.apache.shiro.authc.AuthenticationException;

public class AuthorizeException extends AuthenticationException {
    public AuthorizeException(String message) {
        super(message);
    }

    public AuthorizeException(String message, Throwable e) {
        super(message, e);
    }
}