package com.digiwardrobe.exceptions;

public class AccessoryTypeNotFoundException extends RuntimeException {

    public AccessoryTypeNotFoundException(final int accessoryTypeId) {
        super("Accessory type with id " + accessoryTypeId + " not found");
    }
}
