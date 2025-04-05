package com.digiwardrobe.controllers;

import com.digiwardrobe.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/images/upload")
public class ImageUploadRestController {

    private final FileStorageService fileStorageService;
    private final int unauthorizedCode = 401;
    private static final String MESSAGE_LITERAL = "message";

    @Autowired
    public ImageUploadRestController(final FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/clothingItem")
    public ResponseEntity<Map<String, String>> uploadClothingItemImage(@RequestParam("file") final MultipartFile file) {
        // Authenticate the user
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(unauthorizedCode).body(Map.of(MESSAGE_LITERAL, "Unauthorized"));
        }

        try {
            final String fileName = fileStorageService.storeFile(file, "clothing_items");
            return ResponseEntity.ok(Map.of("fileName", fileName));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE_LITERAL, "File upload failed"));
        }
    }

    @PostMapping("/accessory")
    public ResponseEntity<Map<String, String>> uploadAccessoryImage(@RequestParam("file") final MultipartFile file) {
        // Authenticate the user
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(unauthorizedCode).body(Map.of(MESSAGE_LITERAL, "Unauthorized"));
        }

        try {
            final String fileName = fileStorageService.storeFile(file, "accessories");
            return ResponseEntity.ok(Map.of("fileName", fileName));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE_LITERAL, "File upload failed"));
        }
    }

    @PostMapping("/outfit")
    public ResponseEntity<Map<String, String>> uploadOutfitImage(@RequestParam("file") final MultipartFile file) {
        // Authenticate the user
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(unauthorizedCode).body(Map.of(MESSAGE_LITERAL, "Unauthorized"));
        }

        try {
            final String fileName = fileStorageService.storeFile(file, "outfits");
            return ResponseEntity.ok(Map.of("fileName", fileName));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE_LITERAL, "File upload failed"));
        }
    }
}