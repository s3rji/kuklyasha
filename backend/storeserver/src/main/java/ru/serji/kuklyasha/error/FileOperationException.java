package ru.serji.kuklyasha.error;

import org.springframework.boot.web.error.*;
import org.springframework.http.*;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.*;

public class FileOperationException extends AppException {
    public FileOperationException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}