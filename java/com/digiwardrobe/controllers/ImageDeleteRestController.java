package com.digiwardrobe.controllers;

import com.digiwardrobe.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/images/delete")
public class ImageDeleteRestController {

    private static final String DELETED_LITERAL = "deleted";
    private static final String CLOTHING_ITEMS_LITERAL = "clothing_items";
    private static final String ACCESSORIES_LITERAL = "accessories";
    private static final String OUTFITS_LITERAL = "outfits";
    private static final int UNAUTHORIZED_CODE = 401;

    private final FileStorageService fileStorageService;

    @Autowired
    public ImageDeleteRestController(final FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @DeleteMapping("/clothingItem/{fileName}")
    public ResponseEntity<Map<String, Boolean>> deleteClothingItemImage(@PathVariable final String fileName) {

        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(Map.of(DELETED_LITERAL, false));
        }

        final boolean deleted = fileStorageService.deleteFile(fileName, CLOTHING_ITEMS_LITERAL);
        return ResponseEntity.ok(Map.of(DELETED_LITERAL, deleted));
    }

    @DeleteMapping("/accessory/{fileName}")
    public ResponseEntity<Map<String, Boolean>> deleteAccessoryImage(@PathVariable final String fileName) {

        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(Map.of(DELETED_LITERAL, false));
        }

        final boolean deleted = fileStorageService.deleteFile(fileName, ACCESSORIES_LITERAL);
        return ResponseEntity.ok(Map.of(DELETED_LITERAL, deleted));
    }

    @DeleteMapping("/outfit/{fileName}")
    public ResponseEntity<Map<String, Boolean>> deleteOutfitImage(@PathVariable final String fileName) {

        if (isAuthenticated()) {
            return ResponseEntity.status(UNAUTHORIZED_CODE).body(Map.of(DELETED_LITERAL, false));
        }

        final boolean deleted = fileStorageService.deleteFile(fileName, OUTFITS_LITERAL);
        return ResponseEntity.ok(Map.of(DELETED_LITERAL, deleted));
    }

    private boolean isAuthenticated() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || !authentication.isAuthenticated();
    }
}