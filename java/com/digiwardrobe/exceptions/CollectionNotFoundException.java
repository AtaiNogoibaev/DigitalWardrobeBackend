package com.digiwardrobe.exceptions;

import java.util.UUID;

public class CollectionNotFoundException extends RuntimeException {
    public CollectionNotFoundException(final UUID collectionId) {
        super("Collection with id " + collectionId + " not found");
    }
}
