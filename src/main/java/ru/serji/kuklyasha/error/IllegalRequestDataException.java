package ru.serji.kuklyasha.error;

import org.springframework.boot.web.error.*;
import org.springframework.http.*;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.*;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}