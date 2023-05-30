package ru.serji.kuklyasha.security.exception;

import lombok.*;
import org.springframework.http.*;
import ru.serji.kuklyasha.web.util.*;

import javax.servlet.http.*;
import java.io.*;
import java.time.*;
import java.util.*;

@AllArgsConstructor
public class SecurityExceptionHandler {

    private final HttpStatus status;

    protected void handleException(HttpServletRequest request, HttpServletResponse response, RuntimeException ex) throws IOException {
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
