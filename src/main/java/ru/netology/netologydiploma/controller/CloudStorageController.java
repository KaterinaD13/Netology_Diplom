package ru.netology.netologydiploma.controller;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiploma.model.FileDetails;
import ru.netology.netologydiploma.model.PutRequest;
import ru.netology.netologydiploma.service.StorageService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CloudStorageController {
    private final StorageService storageService;
    private static final Logger log = Logger.getLogger(CloudStorageController.class);

    @GetMapping("/list")
    public List<FileDetails> getFileList(Principal principal,
                                         @RequestParam(name = "limit") int limit) {
        log.info("getFileList by " + principal.getName() + ", limit = " + limit);
        return storageService.getFileList(principal.getName(), limit);
    }

    @PostMapping("/file")
    public void saveFile(Principal principal,
                         @RequestParam("filename") String fileName,
                         @RequestBody MultipartFile file) {
        log.info("saveFile " + fileName + " by " + principal.getName());
        storageService.saveFile(principal.getName(), fileName, file);
    }

    @DeleteMapping("/file")
    public void deleteFile(Principal principal,
                           @RequestParam("filename") String fileName) {
        log.info("DELETE Request: delete file: " + fileName);
        storageService.deleteFile(principal.getName(), fileName);
    }

    @GetMapping("/file")
    public byte[] getFile(Principal principal,
                          @RequestParam("filename") String fileName) {
        log.info("GET Request: download file: " + fileName);
        return storageService.getFile(principal.getName(), fileName);
    }

    @PutMapping("/file")
    public void renameFile(Principal principal,
                           @RequestParam("filename") String oldFileName,
                           @RequestBody PutRequest putRequest) {
        log.info("PUT Request: edit filename: " + oldFileName);
        storageService.renameFile(principal.getName(), oldFileName, putRequest.getFilename());
    }
}