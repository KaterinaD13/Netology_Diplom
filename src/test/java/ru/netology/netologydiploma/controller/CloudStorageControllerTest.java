package ru.netology.netologydiploma.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import ru.netology.netologydiploma.model.FileDetails;
import ru.netology.netologydiploma.model.PutRequest;
import ru.netology.netologydiploma.service.StorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloudStorageControllerTest {
    @Mock
    StorageService storageService;
    @InjectMocks
    CloudStorageController cloudStorageController;

    @Test
    void testGetFileList() {

        ArrayList<FileDetails> fileDetailsList = new ArrayList<>();
        when(storageService.getFileList(Mockito.any(), anyInt())).thenReturn(fileDetailsList);

        List<FileDetails> actualFileList = cloudStorageController.getFileList(new UserPrincipal("principal"), 1);
        assertSame(fileDetailsList, actualFileList);
        assertTrue(actualFileList.isEmpty());
        verify(storageService).getFileList(Mockito.any(), anyInt());
    }

    @Test
    void testSaveFile() throws IOException {
        UserPrincipal principal = new UserPrincipal("John Smith");
        cloudStorageController.saveFile(principal, "foo.txt",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes())));
        verify(storageService).saveFile(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void testGetFile() {
        cloudStorageController.getFile(new UserPrincipal("John Smith"), null);
    }

    @Test
    void testRenameFile() {
        doNothing().when(storageService).renameFile(Mockito.any(), Mockito.any(), Mockito.any());
        UserPrincipal principal = new UserPrincipal("principal");
        cloudStorageController.renameFile(principal, "foo.txt", new PutRequest("foo.txt"));
        verify(storageService).renameFile(Mockito.any(), Mockito.any(), Mockito.any());
    }
}