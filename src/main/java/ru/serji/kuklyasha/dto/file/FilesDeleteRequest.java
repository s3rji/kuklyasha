package ru.serji.kuklyasha.dto.file;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Value
public class FilesDeleteRequest {

    String[] fileNames;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public FilesDeleteRequest(String[] fileNames) {
        this.fileNames = fileNames;
    }
}
