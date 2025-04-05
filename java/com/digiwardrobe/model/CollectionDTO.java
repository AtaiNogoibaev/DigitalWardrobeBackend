package com.digiwardrobe.model;

import java.util.List;
import java.util.UUID;

public record CollectionDTO(
    UUID id,
    UUID userId,
    String name,
    List<UUID> outfitIds
) { }
