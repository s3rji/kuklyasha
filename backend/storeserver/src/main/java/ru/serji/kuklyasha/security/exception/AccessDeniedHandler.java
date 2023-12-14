package ru.serji.kuklyasha.security.exception;

import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.access.*;

import javax.servlet.http.*;
import java.io.*;


@Slf4j
public class AccessDeniedHandler extends SecurityExceptionHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    public AccessDeniedHandler(HttpStatus status) {
        super(status);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        log.error("AccessDeniedException: {}", ex.getMessage());
        handleException(request, response, ex);
    }
}