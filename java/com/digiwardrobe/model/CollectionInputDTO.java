package com.digiwardrobe.model;

import java.util.List;
import java.util.UUID;

public record CollectionInputDTO (
        String name,
        List<UUID> outfitIds
) { }
