package ru.serji.kuklyasha.security.error;

import org.springframework.security.core.*;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
