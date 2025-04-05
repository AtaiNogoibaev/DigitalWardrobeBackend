package com.digiwardrobe.controllers;

import java.util.List;
import java.util.UUID;

import com.digiwardrobe.model.ClothingItemInputDTO;
import com.digiwardrobe.services.ClothingFitService;
import com.digiwardrobe.services.ClothingTypeService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.digiwardrobe.configurations.utils.AuthUtils;
import com.digiwardrobe.data_access.entity.ClothingItemEntity;
import com.digiwardrobe.services.ClothingItemService;

@Controller
public class ClothingItemController {

    private final ClothingItemService clothingItemService;
    private final ClothingTypeService clothingTypeService;
    private final ClothingFitService clothingFitService;

    public ClothingItemController(final ClothingItemService clothingItemService,
                                  final ClothingTypeService clothingTypeService,
                                  final ClothingFitService clothingFitService) {
        this.clothingItemService = clothingItemService;
        this.clothingTypeService = clothingTypeService;
        this.clothingFitService = clothingFitService;
    }

    @QueryMapping
    public List<ClothingItemEntity> userClothingItems() {
        return clothingItemService.getUserClothingItems(getUuid());
    }

    @MutationMapping
    public ClothingItemEntity addClothingItem(@Argument final ClothingItemInputDTO clothingItem) {
        return clothingItemService.addClothingItem(getUuid(), createEntityFromDto(clothingItem));
    }

    @MutationMapping
    public boolean deleteClothingItem(@Argument final UUID clothingItemId) {
        clothingItemService.deleteClothingItem(getUuid(), clothingItemId);
        return true;
    }

    @MutationMapping
    public ClothingItemEntity updateClothingItem(@Argument final UUID clothingItemId,
                                                 @Argument final ClothingItemInputDTO clothingItem) {

        return clothingItemService
                .updateClothingItem(getUuid(), clothingItemId, createEntityFromDto(clothingItem));
    }

    @QueryMapping
    public ClothingItemEntity clothingItem(@Argument final UUID clothingItemId) {
        return clothingItemService.getClothingItemById(getUuid(), clothingItemId);
    }

    private static UUID getUuid() {
        return AuthUtils.getCurrentUserId();
    }

    private ClothingItemEntity createEntityFromDto(final ClothingItemInputDTO clothingItemInputDTO) {
        final ClothingItemEntity clothingItemEntity = new ClothingItemEntity();
        clothingItemEntity.setName(clothingItemInputDTO.name());
        clothingItemEntity.setColor(clothingItemInputDTO.color());
        clothingItemEntity.setBrand(clothingItemInputDTO.brand());
        clothingItemEntity.setSize(clothingItemInputDTO.size());
        clothingItemEntity.setPhotoUrl(clothingItemInputDTO.photoUrl());
        clothingItemEntity
                .setClothingFit(clothingFitService.getClothingFit(clothingItemInputDTO.clothingFitId()));
        clothingItemEntity
                .setClothingType(clothingTypeService.getClothingType(clothingItemInputDTO.clothingTypeId()));

        return clothingItemEntity;
    }

}
