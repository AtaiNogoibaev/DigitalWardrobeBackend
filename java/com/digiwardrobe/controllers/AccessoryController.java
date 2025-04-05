package com.digiwardrobe.controllers;

import java.util.List;
import java.util.UUID;

import com.digiwardrobe.data_access.entity.AccessoryEntity;
import com.digiwardrobe.model.AccessoryInputDTO;
import com.digiwardrobe.services.AccessoryService;
import com.digiwardrobe.services.AccessoryTypeService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.digiwardrobe.configurations.utils.AuthUtils;

@Controller
public class AccessoryController {

    private final AccessoryService accessoryService;
    private final AccessoryTypeService accessoryTypeService;


    public AccessoryController(final AccessoryService accessoryService,
                               final AccessoryTypeService accessoryTypeService) {
     this.accessoryTypeService = accessoryTypeService;
     this.accessoryService = accessoryService;
    }

    @QueryMapping
    public List<AccessoryEntity> userAccessories() {
        return accessoryService.getAllAccessory(getUuid());
    }

    @QueryMapping
    public AccessoryEntity accessory(@Argument final UUID accessoryId) {
        return accessoryService.getAccessoryById(getUuid(), accessoryId);
    }

    @MutationMapping
    public AccessoryEntity addAccessory(@Argument final AccessoryInputDTO accessory) {
        return accessoryService.addAccessory(getUuid(), createEntityFromDto(accessory));
    }

    @MutationMapping
    public boolean deleteAccessory(@Argument final UUID accessoryId) {
        accessoryService.deleteAccessory(getUuid(), accessoryId);
        return true;
    }

    @MutationMapping
    public AccessoryEntity updateAccessory(@Argument final UUID accessoryId,
                                                 @Argument final AccessoryInputDTO accessory) {
        return accessoryService.updateAccessory(getUuid(), accessoryId, createEntityFromDto(accessory));
    }

    private static UUID getUuid() {
        return AuthUtils.getCurrentUserId();
    }

    private AccessoryEntity createEntityFromDto(final AccessoryInputDTO accessoryInputDTO) {
        final AccessoryEntity accessoryEntity = new AccessoryEntity();
        accessoryEntity.setName(accessoryInputDTO.name());
        accessoryEntity.setBrand(accessoryInputDTO.brand());
        accessoryEntity.setColor(accessoryInputDTO.color());
        accessoryEntity.setAccessoryType(accessoryTypeService.getAccessoryTypeById(accessoryInputDTO.accessoryType()));
        accessoryEntity.setPhotoUrl(accessoryInputDTO.photoUrl());
        return accessoryEntity;
    }

}
