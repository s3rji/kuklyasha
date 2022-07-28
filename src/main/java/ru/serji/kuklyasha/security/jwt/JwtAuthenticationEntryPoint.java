package ru.serji.kuklyasha.security.jwt;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import ru.serji.kuklyasha.util.*;

import javax.servlet.http.*;
import java.io.*;
import java.time.*;
import java.util.*;

@AllArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HttpStatus status;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        log.error("AuthenticationException: {}", ex.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        JsonUtil.writeValue(response.getOutputStream(), body);
    }
}