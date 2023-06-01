package ru.serji.kuklyasha.web;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.mock.web.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.FileTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;

public class FileControllerTest extends AbstractControllerTest {

    @MockBean
    private FileService fileService;

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void save() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", FILE_NAME, MediaType.TEXT_PLAIN_VALUE, "this is a test".getBytes());
        Mockito.doReturn(FILE_NAME).when(fileService).save(file);
        perform(multipart(FileController.REST_URL + "/upload").file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(FILE_UPLOAD_MATCHER.contentJson(fileUploadResponse));
    }

    @Test
    void saveFailed() throws Exception {
        Mockito.doReturn(FILE_NAME).when(fileService).save(multipartFile);
        perform(multipart(FileController.REST_URL + "/upload").file(multipartFile))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void delete() throws Exception {
        Mockito.doNothing().when(fileService).delete(filesDeleteRequest.getFileNames());
        perform(MockMvcRequestBuilders.delete(FileController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(filesDeleteRequest)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFailed() throws Exception {
        Mockito.doNothing().when(fileService).delete(filesDeleteRequest.getFileNames());
        perform(MockMvcRequestBuilders.delete(FileController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(filesDeleteRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void throwExceptionWhileDeleting() throws Exception {
        Mockito.doThrow(new FileOperationException("Could not delete files"))
                .when(fileService).delete(filesDeleteRequest.getFileNames());

        perform(MockMvcRequestBuilders.delete(FileController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(filesDeleteRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void deleteOneFile() throws Exception {
        Mockito.doNothing().when(fileService).delete(FILE_NAME);
        perform(MockMvcRequestBuilders.delete(FileController.REST_URL + "/" + FILE_NAME))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}