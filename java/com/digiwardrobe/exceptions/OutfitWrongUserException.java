package com.digiwardrobe.exceptions;

import java.util.UUID;

public class OutfitWrongUserException extends RuntimeException {

    public OutfitWrongUserException(final UUID userId, final UUID outfitId) {
        super(String.format("User with id %s does not have outfit id %s", userId, outfitId));
    }
}
