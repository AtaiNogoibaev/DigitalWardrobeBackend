package com.digiwardrobe.services;

import com.digiwardrobe.data_access.entity.AccessoryEntity;
import com.digiwardrobe.data_access.entity.ClothingItemEntity;
import com.digiwardrobe.data_access.entity.OutfitEntity;
import com.digiwardrobe.data_access.entity.OutfitItemsEntity;

import com.digiwardrobe.data_access.repositories.AccessoryRepository;
import com.digiwardrobe.data_access.repositories.ClothingItemRepository;
import com.digiwardrobe.data_access.repositories.OutfitItemsRepository;
import com.digiwardrobe.data_access.repositories.OutfitRepository;

import com.digiwardrobe.exceptions.ClothingItemNotFoundException;
import com.digiwardrobe.exceptions.ClothingItemWrongUserException;
import com.digiwardrobe.exceptions.EitherClothingOrAccessoryException;
import com.digiwardrobe.exceptions.NullClothingAndAccessoryException;
import com.digiwardrobe.exceptions.OutfitNotFoundException;
import com.digiwardrobe.exceptions.AccessoryNotFoundException;
import com.digiwardrobe.exceptions.AccessoryWrongUserException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OutfitItemsService {

    private final OutfitItemsRepository outfitItemsRepository;
    private final OutfitRepository outfitRepository;
    private final ClothingItemRepository clothingItemRepository;
    private final AccessoryRepository accessoryRepository;

    public OutfitItemsService(final OutfitItemsRepository outfitItemsRepository,
                              final OutfitRepository outfitRepository,
                              final ClothingItemRepository clothingItemRepository,
                              final AccessoryRepository accessoryRepository) {
        this.outfitItemsRepository = outfitItemsRepository;
        this.outfitRepository = outfitRepository;
        this.clothingItemRepository = clothingItemRepository;
        this.accessoryRepository = accessoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ClothingItemEntity> getClothingItemsByOutfitId(final UUID outfitId) {
        final List<OutfitItemsEntity> outfitItems = outfitItemsRepository.findClothingItemsByOutfitId(outfitId);
        return outfitItems.stream()
                .map(OutfitItemsEntity::getClothingItem)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AccessoryEntity> getAccessoriesByOutfitId(final UUID outfitId) {
        final List<OutfitItemsEntity> outfitItems = outfitItemsRepository.findAccessoriesByOutfitId(outfitId);
        return outfitItems.stream()
                .map(OutfitItemsEntity::getAccessory)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addItemToOutfit(final OutfitEntity outfit,
                                final UUID clothingItemId,
                                final UUID accessoryId,
                                final UUID userId) {

        if ((clothingItemId != null && accessoryId != null)) {
            throw new EitherClothingOrAccessoryException(outfit.getId());
        } else if (clothingItemId == null && accessoryId == null) {
            throw new NullClothingAndAccessoryException(outfit.getId());
        }

        final OutfitItemsEntity outfitItem = new OutfitItemsEntity();
        outfitItem.setOutfit(outfit);

        if (clothingItemId != null) {
            final ClothingItemEntity clothingItem = clothingItemRepository.findById(clothingItemId)
                    .orElseThrow(() -> new ClothingItemNotFoundException(clothingItemId));

            if (!clothingItem.getUserId().equals(userId)) {
                throw new ClothingItemWrongUserException(userId, clothingItemId);
            }
            outfitItem.setClothingItem(clothingItem);
        } else {
            final AccessoryEntity accessory = accessoryRepository.findById(accessoryId)
                    .orElseThrow(() -> new AccessoryNotFoundException(accessoryId));

            if (!accessory.getUser().getId().equals(userId)) {
                throw new AccessoryWrongUserException(userId, accessoryId);
            }
            outfitItem.setAccessory(accessory);
        }

        outfitItemsRepository.save(outfitItem);
    }

    @Transactional
    public void deleteOutfitItemsByOutfitId(final UUID outfitId) {
        final OutfitEntity outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() -> new OutfitNotFoundException(outfitId));

        outfitItemsRepository.deleteByOutfit(outfit);
    }

    @Transactional
    public void replaceOutfitItemsByOutfitId(final OutfitEntity outfit,
                                             final List<UUID> clothingItems,
                                             final List<UUID> accessories,
                                             final UUID userId) {
        deleteOutfitItemsByOutfitId(outfit.getId());
        if (clothingItems != null) {
            for (final UUID clothingItemId : clothingItems) {
                addItemToOutfit(outfit, clothingItemId, null, userId);
            }
        }
        if (accessories != null) {
            for (final UUID accessoryId : accessories) {
                addItemToOutfit(outfit, null, accessoryId, userId);
            }
        }

    }
}