package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.multipart.*;
import ru.serji.kuklyasha.error.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class FileService {

    @Value("${upload.images.path}")
    private String uploadPath;

    @Value("${upload.content.allowed-types}")
    private List<String> allowedTypes;

    public String save(MultipartFile multipartFile) {
        if (isNotValidContentType(multipartFile)) {
            throw new FileUploadException("Allowed " + allowedTypes + " files only");
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path path = Paths.get(uploadPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String fileName = UUID.randomUUID() + "." + fileExtension;
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            throw new FileUploadException("Could not save file: " + multipartFile.getOriginalFilename());
        }
    }

    private boolean isNotValidContentType(MultipartFile multipartFile) {
        return !allowedTypes.contains(multipartFile.getContentType());
    }
}
