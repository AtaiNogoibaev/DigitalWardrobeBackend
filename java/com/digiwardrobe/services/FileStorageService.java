package com.digiwardrobe.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageService.class);

    public String storeFile(final MultipartFile file, final String subDirectory) throws IOException {
        final String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        final Path filePath = Paths.get(uploadDirectory, subDirectory, fileName);
        try {
            Files.createDirectories(Paths.get(uploadDirectory, subDirectory));
            Files.write(filePath, file.getBytes());
            return fileName;
        } catch (IOException e) {
            LOGGER.error("Error storing file: {}", e.getMessage());
            throw e;
        }
    }

    public byte[] readFile(final String fileName, final String subDirectory) throws IOException {
        final Path filePath = Paths.get(uploadDirectory, subDirectory, fileName);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            LOGGER.error("Error reading file: {}", e.getMessage());
            throw e;
        }
    }

    public boolean deleteFile(final String fileName, final String subDirectory) {
        try {
            final Path filePath = Paths.get(uploadDirectory, subDirectory, fileName);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            LOGGER.error("Error deleting file: {}", e.getMessage());
            return false;
        }
    }

    public String updateFile(final MultipartFile file, final String oldFileName,
                             final String subDirectory) throws IOException {
        try {
            deleteFile(oldFileName, subDirectory);
            return storeFile(file, subDirectory);
        } catch (IOException e) {
            LOGGER.error("Error updating file: {}", e.getMessage());
            throw e;
        }
    }
}