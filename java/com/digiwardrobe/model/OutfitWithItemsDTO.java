package com.digiwardrobe.model;

import com.digiwardrobe.data_access.entity.AccessoryEntity;
import com.digiwardrobe.data_access.entity.ClothingItemEntity;

import java.util.List;
import java.util.UUID;

public record OutfitWithItemsDTO(
        @SuppressWarnings("PMD.ShortVariable")
        UUID id,
        String name,
        String season,
        String style,
        String photoUrl,
        List<ClothingItemEntity> clothingItems,
        List<AccessoryEntity> accessories
) { }
