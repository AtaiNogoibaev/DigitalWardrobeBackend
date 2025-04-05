package com.digiwardrobe.exceptions;

import java.util.UUID;

public class CollectionWrongUserException extends RuntimeException {

    public CollectionWrongUserException(final UUID userId, final UUID collectionId) {
        super(String.format("User %s does not have collection id %s", userId, collectionId));
    }
}
