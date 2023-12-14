package ru.serji.kuklyasha.security.exception;

import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import ru.serji.kuklyasha.security.exception.*;

import javax.servlet.http.*;
import java.io.*;

@Slf4j
public class JwtAuthenticationEntryPoint extends SecurityExceptionHandler implements AuthenticationEntryPoint {

    public JwtAuthenticationEntryPoint(HttpStatus status) {
        super(status);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        log.error("AuthenticationException: {}", ex.getMessage());
        handleException(request, response, ex);
    }
}