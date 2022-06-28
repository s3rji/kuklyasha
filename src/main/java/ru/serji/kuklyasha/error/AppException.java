package ru.serji.kuklyasha.error;

import lombok.*;
import org.springframework.boot.web.error.*;
import org.springframework.http.*;
import org.springframework.web.server.*;

@Getter
public class AppException extends ResponseStatusException {
    private final ErrorAttributeOptions options;

    public AppException(HttpStatus status, String message, ErrorAttributeOptions options) {
        super(status, message);
        this.options = options;
    }

    @Override
    public String getMessage() {
        return getReason();
    }
}