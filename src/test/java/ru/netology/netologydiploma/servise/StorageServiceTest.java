package ru.netology.netologydiploma.servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiploma.exceprion.InputDataException;
import ru.netology.netologydiploma.model.FileDetails;
import ru.netology.netologydiploma.reposipory.StorageRepository;
import ru.netology.netologydiploma.service.StorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {StorageService.class})
@ExtendWith(SpringExtension.class)
class StorageServiceTest {
    @MockBean
    private StorageRepository storageRepository;

    @Autowired
    private StorageService storageService;

    @Test
    void testGetFileList() {
        ArrayList<FileDetails> fileDetailsList = new ArrayList<>();
        when(storageRepository.getFileList(Mockito.<String>any(), anyInt())).thenReturn(fileDetailsList);
        List<FileDetails> actualFileList = storageService.getFileList("UserLogin", 1);
        assertSame(fileDetailsList, actualFileList);
        assertTrue(actualFileList.isEmpty());
        verify(storageRepository).getFileList(Mockito.<String>any(), anyInt());
    }

    @Test
    void testGetFileListInputDataException() {
        when(storageRepository.getFileList(Mockito.<String>any(), anyInt())).thenThrow(new InputDataException(" "));
        assertThrows(InputDataException.class, () -> storageService.getFileList("UserLogin", 1));
        verify(storageRepository).getFileList(Mockito.<String>any(), anyInt());
    }

    @Test
    void testSaveFile() throws IOException {
        doNothing().when(storageRepository).saveFile(Mockito.<String>any(), Mockito.<String>any(), Mockito.<MultipartFile>any());
        storageService.saveFile("Login", "foo.txt", new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes())));
        verify(storageRepository).saveFile(Mockito.<String>any(), Mockito.<String>any(), Mockito.<MultipartFile>any());
    }

    @Test
    void testSaveFileInputDataException() {
        assertThrows(InputDataException.class, () -> storageService.saveFile("Login", null, new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes()))));
    }

    @Test
    void testDeleteFile() {
        doNothing().when(storageRepository).deleteFile(Mockito.<String>any(), Mockito.<String>any());
        storageService.deleteFile("Login", "foo.txt");
        verify(storageRepository).deleteFile(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testDeleteFileInvalidFileName() {
        assertThrows(InputDataException.class, () -> storageService.deleteFile("Login", null));
    }

    @Test
    void testDeleteFileInvalidFile() {
        doThrow(new InputDataException("Msg")).when(storageRepository).deleteFile(Mockito.<String>any(), Mockito.<String>any());
        assertThrows(InputDataException.class, () -> storageService.deleteFile("Login", "foo.txt"));
        verify(storageRepository).deleteFile(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testGetFile() {
        when(storageRepository.getFile(Mockito.<String>any(), Mockito.<String>any())).thenReturn("AXAXAXAX".getBytes());
        byte[] actualFile = storageService.getFile("Login", "foo.txt");
        assertEquals(8, actualFile.length);
        verify(storageRepository).getFile(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testGetFileInvalidFileName() {
        assertThrows(InputDataException.class, () -> storageService.getFile("Login", null));
    }

    @Test
    void testGetFileInvalidFile() {
        when(storageRepository.getFile(Mockito.<String>any(), Mockito.<String>any())).thenThrow(new InputDataException(" "));
        assertThrows(InputDataException.class, () -> storageService.getFile("Login", "foo.txt"));
        verify(storageRepository).getFile(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testRenameFile() {
        doNothing().when(storageRepository).renameFile(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        storageService.renameFile("Login", "foo.txt", "foo.txt");
        verify(storageRepository).renameFile(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testRenameFileInvalidFileName() {
        assertThrows(InputDataException.class, () -> storageService.renameFile("Login", null, "foo.txt"));
    }

    @Test
    void testRenameFileInvalidNewFileName() {
        assertThrows(InputDataException.class, () -> storageService.renameFile("Login", "foo.txt", null));
    }

}