package ru.serji.kuklyasha.security.error;

import org.springframework.boot.web.error.*;
import org.springframework.http.*;
import ru.serji.kuklyasha.error.*;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.*;

public class JwtAuthenticationException extends AppException {
    public JwtAuthenticationException(String msg) {
        super(HttpStatus.UNAUTHORIZED, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}