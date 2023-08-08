package ru.netology.netologydiploma.reposipory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiploma.exceprion.FileException;
import ru.netology.netologydiploma.exceprion.InputDataException;
import ru.netology.netologydiploma.model.FileDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StorageRepository {
    @Value("${cloud.storeDirectory}")
    private String directory;
    private static final Logger log = Logger.getLogger(StorageRepository.class);

    private String createPath(String login) {
        return directory + File.separator + login;
    }

    private File createFile(String login, String fileName) {
        return new File(createPath(login) + File.separator + fileName);
    }

    private boolean checkFileInStorage(File file) {
        return file.exists();
    }

    public List<FileDetails> getFileList(String login, int limit) {
        try {
            File path = new File(createPath(login));
            File[] files = path.listFiles();
            List<FileDetails> resultList = new ArrayList<>();

            if (files != null) {
                for (File file : files) {
                    if (checkFileInStorage(file)) {
                        resultList.add(new FileDetails(file.getName(), (int) file.length()));
                        limit--;
                    }
                    if (limit == 0) {
                        break;
                    }
                }
            }

            log.info("getFileList by " + login + " complete");
            return resultList;

        } catch (Exception e) {
            log.error("Error getting file list " + e.getMessage());
            throw new FileException("Error getting file list");
        }
    }

    public void saveFile(String login, String fileName, MultipartFile fileToSave) {
        File file = createFile(login, fileName);
        if (checkFileInStorage(file)) {
            String newFileName = 0 + fileName;
            saveFile(login, newFileName, fileToSave);
        }
        try {
            Files.createDirectories(Paths.get(createPath(login)));
            FileOutputStream outputStream = new FileOutputStream(file);

            outputStream.write(fileToSave.getBytes());
            outputStream.close();
            log.info("File " + fileName + " saved");

        } catch (IOException e) {
            log.error("Failed to save file " + fileName);
            throw new InputDataException("Error input data");
        }
    }

    public void deleteFile(String login, String fileName) {
        File file = createFile(login, fileName);
        if (checkFileInStorage(file)) {
            if (file.delete()) {
                log.info("delete file " + fileName);
            }
        } else {
            log.error("Error deleting file " + fileName);
            throw new FileException("Error deleting file");
        }
    }

    public byte[] getFile(String login, String fileName) {
        File file = createFile(login, fileName);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            log.error("Error upload file " + fileName);
            throw new FileException("Error upload file");
        }
    }

    public void renameFile(String login, String fileName, String newFileName) {
        File file = createFile(login, fileName);
        File newFile = createFile(login, newFileName);
        if (file.renameTo(newFile)) {
            log.info("File " + fileName + "rename to " + newFileName);
        } else {
            log.error("Error rename file " + fileName);
            throw new FileException("Error upload file");
        }
    }
}