package com.digiwardrobe.controllers;

import com.digiwardrobe.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/images/update")
public class ImageUpdateRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUpdateRestController.class);
    private static final String MESSAGE_LITERAL = "message";
    private static final String FILE_NAME_LITERAL = "fileName";
    private static final String CLOTHING_ITEMS_LITERAL = "clothing_items";
    private static final String ACCESSORIES_LITERAL = "accessories";
    private static final String OUTFITS_LITERAL = "outfits";
    private static final int UNAUTHORIZED_CODE = 401;

    private final FileStorageService fileStorageService;

    @Autowired
    public ImageUpdateRestController(final FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PutMapping("/clothingItem/{oldFileName}")
    public ResponseEntity<Map<String, String>> updateClothingItemImage(
            @RequestParam("file") final MultipartFile file,
            @PathVariable final String oldFileName) {

        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(Map.of(MESSAGE_LITERAL, "Unauthorized"));
        }

        try {
            final String fileName = fileStorageService.updateFile(file, oldFileName, CLOTHING_ITEMS_LITERAL);
            return ResponseEntity.ok(Map.of(FILE_NAME_LITERAL, fileName));
        } catch (final IOException e) {
            LOGGER.error("Error updating clothing item image: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(MESSAGE_LITERAL, "File update failed"));
        }
    }

    @PutMapping("/accessory/{oldFileName}")
    public ResponseEntity<Map<String, String>> updateAccessoryImage(
            @RequestParam("file") final MultipartFile file,
            @PathVariable final String oldFileName) {

        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(Map.of(MESSAGE_LITERAL, "Unauthorized"));
        }

        try {
            final String fileName = fileStorageService.updateFile(file, oldFileName, ACCESSORIES_LITERAL);
            return ResponseEntity.ok(Map.of(FILE_NAME_LITERAL, fileName));
        } catch (final IOException e) {
            LOGGER.error("Error updating accessory image: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(MESSAGE_LITERAL, "File update failed"));
        }
    }

    @PutMapping("/outfit/{oldFileName}")
    public ResponseEntity<Map<String, String>> updateOutfitImage(
            @RequestParam("file") final MultipartFile file,
            @PathVariable final String oldFileName) {

        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(Map.of(MESSAGE_LITERAL, "Unauthorized"));
        }

        try {
            final String fileName = fileStorageService.updateFile(file, oldFileName, OUTFITS_LITERAL);
            return ResponseEntity.ok(Map.of(FILE_NAME_LITERAL, fileName));
        } catch (final IOException e) {
            LOGGER.error("Error updating outfit image: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(MESSAGE_LITERAL, "File update failed"));
        }
    }

    private boolean isAuthenticated() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || !authentication.isAuthenticated();
    }
}