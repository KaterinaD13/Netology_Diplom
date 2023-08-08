package ru.netology.netologydiploma.reposipory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.netology.netologydiploma.exceprion.FileException;
import ru.netology.netologydiploma.exceprion.InputDataException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {StorageRepository.class})
@ExtendWith(SpringExtension.class)
class StorageRepositoryTest {
    @Autowired
    private StorageRepository storageRepository;


//    @Test
//    void testSaveFileValidData() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
//                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
//            storageRepository.saveFile("Login", "foo.txt",
//                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes())));
//        }
//    }

    @Test
    void testSaveFileInvalidDataFileException() {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenThrow(new FileException(" "));
            assertThrows(FileException.class, () -> storageRepository.saveFile("Login", "foo.txt",
                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes()))));
            mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
        }
    }


//    @Test
//    void testSaveFile() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
//                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
//            storageRepository.saveFile("Login", "testFile",
//                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes())));
//        }
//    }

//    @Test
//    void testSaveFileInvalidDataInputDataException() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
//                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
//            MockMultipartFile fileToSave = mock(MockMultipartFile.class);
//            when(fileToSave.getBytes()).thenThrow(new IOException("foo"));
//            assertThrows(InputDataException.class, () -> storageRepository.saveFile("", "foo.txt", fileToSave));
//            mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
//            verify(fileToSave).getBytes();
//        }
//    }

    @Test
    void testDeleteFile() {
        assertThrows(FileException.class, () -> storageRepository.deleteFile("Login1", "foo.txt"));
    }

    @Test
    void testGetFile() {
        assertThrows(FileException.class, () -> storageRepository.getFile("Login2", "foo.txt"));
    }

//    @Test
//    void testRenameFileValidData() {
//        storageRepository.renameFile("Login", "foo.txt", "foo.txt");
//    }

    @Test
    void testRenameFileInvalidData() {
        assertThrows(FileException.class, () -> storageRepository.renameFile("Login2", "foo.txt", "foo.txt"));
    }
}