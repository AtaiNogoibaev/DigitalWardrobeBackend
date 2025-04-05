package com.digiwardrobe.model;

public record ClothingItemInputDTO(
        String name,
        String color,
        String brand,
        String size,
        String photoUrl,
        int clothingTypeId,
        int clothingFitId
) { }