package com.digiwardrobe.exceptions;

import java.util.UUID;

public class OutfitNotFoundException extends RuntimeException {

    public OutfitNotFoundException(final UUID outfitId) {
        super(String.format("Outfit with id %s not found", outfitId));
    }
}
