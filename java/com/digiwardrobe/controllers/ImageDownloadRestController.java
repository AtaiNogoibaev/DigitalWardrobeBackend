package com.digiwardrobe.controllers;

import com.digiwardrobe.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/images/download")
public class ImageDownloadRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDownloadRestController.class);
    private static final String CLOTHING_ITEMS_LITERAL = "clothing_items";
    private static final String ACCESSORIES_LITERAL = "accessories";
    private static final String OUTFITS_LITERAL = "outfits";
    private static final int UNAUTHORIZED_CODE = 401;

    private final FileStorageService fileStorageService;

    @Autowired
    public ImageDownloadRestController(final FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/clothingItem/{fileName}")
    public ResponseEntity<ByteArrayResource> getClothingItemImage(@PathVariable final String fileName) {
        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(null);
        }

        try {
            final byte[] imageData = fileStorageService.readFile(fileName, CLOTHING_ITEMS_LITERAL);
            final ByteArrayResource resource = new ByteArrayResource(imageData);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (final IOException e) {
            LOGGER.error("Error downloading clothing item image: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/accessory/{fileName}")
    public ResponseEntity<ByteArrayResource> getAccessoryImage(@PathVariable final String fileName) {
        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(null);
        }

        try {
            final byte[] imageData = fileStorageService.readFile(fileName, ACCESSORIES_LITERAL);
            final ByteArrayResource resource = new ByteArrayResource(imageData);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (final IOException e) {
            LOGGER.error("Error downloading accessory image: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/outfit/{fileName}")
    public ResponseEntity<ByteArrayResource> getOutfitImage(@PathVariable final String fileName) {
        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(null);
        }

        try {
            final byte[] imageData = fileStorageService.readFile(fileName, OUTFITS_LITERAL);
            final ByteArrayResource resource = new ByteArrayResource(imageData);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (final IOException e) {
            LOGGER.error("Error downloading outfit image: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    private boolean isAuthenticated() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || !authentication.isAuthenticated();
    }
}