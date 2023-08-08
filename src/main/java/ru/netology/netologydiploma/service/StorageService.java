package ru.netology.netologydiploma.service;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiploma.model.FileDetails;
import ru.netology.netologydiploma.exceprion.InputDataException;
import ru.netology.netologydiploma.reposipory.StorageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private static final Logger log = Logger.getLogger(StorageService.class);

    private void checkFileName(String fileName) {
        if (fileName == null) {
            log.error("Error input data");
            throw new InputDataException("Error input data");
        }
    }

    public List<FileDetails> getFileList(String userLogin, int limit) {
        log.info("getFileList by " + userLogin);
        return storageRepository.getFileList(userLogin, limit);
    }

    public void saveFile(String login, String fileName, MultipartFile file) {
        checkFileName(fileName);
        storageRepository.saveFile(login, fileName, file);
    }

    public void deleteFile(String login, String fileName) {
        checkFileName(fileName);
        storageRepository.deleteFile(login, fileName);

    }

    public byte[] getFile(String login, String fileName) {
        checkFileName(fileName);
        return storageRepository.getFile(login, fileName);
    }

    public void renameFile(String login, String fileName, String newFileName) {
        checkFileName(fileName);
        checkFileName(newFileName);
        storageRepository.renameFile(login, fileName, newFileName);
    }
}