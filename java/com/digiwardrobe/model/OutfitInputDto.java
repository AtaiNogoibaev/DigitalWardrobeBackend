package com.digiwardrobe.model;

import java.util.List;
import java.util.UUID;

public record OutfitInputDto(
        String name,
        String season,
        String style,
        String photoUrl,
        List<UUID> clothingItemsId,
        List<UUID> accessoriesId
) { }
