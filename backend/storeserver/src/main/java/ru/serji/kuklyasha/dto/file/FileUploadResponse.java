package ru.serji.kuklyasha.dto.file;

import lombok.*;

@Value
public class FileUploadResponse {

    String fileName;
    long size;
}
