package ru.serji.kuklyasha;

import ru.serji.kuklyasha.dto.*;

import java.util.*;

public class FileTestData {

    public static final MatcherFactory.Matcher<FileUploadResponse> FILE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(FileUploadResponse.class, "size");

    public static final String FILE_NAME = UUID.randomUUID() + "." + "jpg";

    public static final FileUploadResponse fileUploadResponse = new FileUploadResponse(FILE_NAME, 0);
}
