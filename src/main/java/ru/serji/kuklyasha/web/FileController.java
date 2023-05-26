package ru.serji.kuklyasha.web;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import ru.serji.kuklyasha.config.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.service.*;

import static ru.serji.kuklyasha.web.FileController.*;

@RestController
@RequestMapping(value = REST_URL)
@Slf4j
@CrossOrigin(origins = ReactAppProperties.HOST_NAME)
public class FileController {

    static final String REST_URL = "/api/admin/file";

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        log.debug("Upload file '{}'", multipartFile);

        long size = multipartFile.getSize();
        String fileName = fileService.save(multipartFile);
        FileUploadResponse response = new FileUploadResponse(fileName, size);
        return ResponseEntity.ok(response);
    }
}