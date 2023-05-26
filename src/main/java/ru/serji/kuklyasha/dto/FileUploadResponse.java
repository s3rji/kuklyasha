package ru.serji.kuklyasha.dto;

import lombok.*;

@Value
public class FileUploadResponse {

    String fileName;
    long size;
}
