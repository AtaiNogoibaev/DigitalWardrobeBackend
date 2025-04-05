package com.digiwardrobe.services;

import java.util.List;
import java.util.UUID;

import com.digiwardrobe.exceptions.ClothingItemNotFoundException;
import com.digiwardrobe.exceptions.ClothingItemWrongUserException;
import com.digiwardrobe.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import com.digiwardrobe.data_access.entity.ClothingItemEntity;
import com.digiwardrobe.data_access.entity.UserEntity;
import com.digiwardrobe.data_access.repositories.ClothingItemRepository;
import com.digiwardrobe.data_access.repositories.UserRepository;

@Service
public class ClothingItemService {

    private final ClothingItemRepository clothingItemRepository;
    private final UserRepository userRepository;

    public ClothingItemService(final ClothingItemRepository clothingItemRepository,
                               final UserRepository userRepository) {
        this.clothingItemRepository = clothingItemRepository;
        this.userRepository = userRepository;
    }

    public List<ClothingItemEntity> getUserClothingItems(final UUID userId) {
        return clothingItemRepository.findAllByUser_Id(userId);
    }

    public ClothingItemEntity getClothingItemById(final UUID userId, final UUID clothingItemId) {
        final ClothingItemEntity clothingItem = findClothingItemById(clothingItemId);

        if (!clothingItem.getUserId().equals(userId)) {
            throw new ClothingItemWrongUserException(userId, clothingItemId);
        }

        return clothingItem;
    }

    public ClothingItemEntity addClothingItem(final UUID userId, final ClothingItemEntity clothingItem) {
        clothingItem.setUser(findUserById(userId));
        return clothingItemRepository.save(clothingItem);
    }

    public void deleteClothingItem(final UUID userId, final UUID clothingItemId) {
        final ClothingItemEntity clothingItem = findClothingItemById(clothingItemId);

        if (!clothingItem.getUserId().equals(userId)) {
            throw new ClothingItemWrongUserException(userId, clothingItemId);
        }

        clothingItemRepository.deleteById(clothingItemId);
    }

    public ClothingItemEntity updateClothingItem(final UUID userId, final UUID clothingItemId,
                                                 final ClothingItemEntity clothingItemDetails) {
        final ClothingItemEntity clothingItem = clothingItemRepository.findById(clothingItemId)
                .orElseThrow(() -> new ClothingItemNotFoundException(clothingItemId));

        if (!clothingItem.getUserId().equals(userId)) {
            throw new ClothingItemWrongUserException(userId, clothingItemId);
        }

        if (clothingItemDetails.getName() != null) {
            clothingItem.setName(clothingItemDetails.getName());
        }
        if (clothingItemDetails.getColor() != null) {
            clothingItem.setColor(clothingItemDetails.getColor());
        }
        if (clothingItemDetails.getBrand() != null) {
            clothingItem.setBrand(clothingItemDetails.getBrand());
        }
        if (clothingItemDetails.getSize() != null) {
            clothingItem.setSize(clothingItemDetails.getSize());
        }
        if (clothingItemDetails.getClothingType() != null) {
            clothingItem.setClothingType(clothingItemDetails.getClothingType());
        }
        if (clothingItemDetails.getClothingFit() != null) {
            clothingItem.setClothingFit(clothingItemDetails.getClothingFit());
        }
        if (clothingItemDetails.getPhotoUrl() != null) {
            clothingItem.setPhotoUrl(clothingItemDetails.getPhotoUrl());
        }

        return clothingItemRepository.save(clothingItem);
    }

    ClothingItemEntity findClothingItemById(final UUID clothingItemId) {
        return clothingItemRepository.findById(clothingItemId)
                .orElseThrow(() -> new ClothingItemNotFoundException(clothingItemId));
    }

    UserEntity findUserById(final UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

}
