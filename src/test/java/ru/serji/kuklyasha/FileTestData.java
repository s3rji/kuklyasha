package ru.serji.kuklyasha;

import org.springframework.http.*;
import org.springframework.mock.web.*;
import ru.serji.kuklyasha.dto.file.*;

import java.util.*;

public class FileTestData {

    public static final MatcherFactory.Matcher<FileUploadResponse> FILE_UPLOAD_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(FileUploadResponse.class, "size");
    public static final MatcherFactory.Matcher<FilesDeleteRequest> FILE_DELETE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(FilesDeleteRequest.class);

    public static final String FILE_NAME = UUID.randomUUID() + "." + "jpg";

    public static final MockMultipartFile multipartFile = new MockMultipartFile("file", FILE_NAME, MediaType.TEXT_PLAIN_VALUE, "this is a test".getBytes());
    public static final FileUploadResponse fileUploadResponse = new FileUploadResponse(FILE_NAME, 0);
    public static final FilesDeleteRequest filesDeleteRequest = new FilesDeleteRequest(new String[]{FILE_NAME});
}
