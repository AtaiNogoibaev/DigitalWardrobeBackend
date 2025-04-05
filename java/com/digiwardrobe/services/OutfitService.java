package com.digiwardrobe.services;

import java.util.List;
import java.util.UUID;

import com.digiwardrobe.data_access.entity.AccessoryEntity;
import com.digiwardrobe.data_access.entity.ClothingItemEntity;
import com.digiwardrobe.data_access.entity.UserEntity;
import com.digiwardrobe.data_access.repositories.UserRepository;
import com.digiwardrobe.exceptions.AccessoryWrongUserException;
import com.digiwardrobe.exceptions.OutfitNotFoundException;
import com.digiwardrobe.exceptions.OutfitWrongUserException;
import com.digiwardrobe.exceptions.UserNotFoundException;
import com.digiwardrobe.model.OutfitInputDto;
import com.digiwardrobe.model.OutfitWithItemsDTO;
import org.springframework.stereotype.Service;

import com.digiwardrobe.data_access.entity.OutfitEntity;
import com.digiwardrobe.data_access.repositories.OutfitRepository;

@Service
public class OutfitService {

    private final OutfitRepository outfitRepository;
    private final OutfitItemsService outfitItemsService;
    private final UserRepository userRepository;

    public OutfitService(final OutfitRepository outfitRepository,
                         final OutfitItemsService outfitItemsService,
                         final UserRepository userRepository) {

        this.outfitRepository = outfitRepository;
        this.outfitItemsService = outfitItemsService;
        this.userRepository = userRepository;
    }

    public List<OutfitEntity> getUserOutfits(final UUID userId) {
        return outfitRepository.findAllByUserId(userId);
    }

    public OutfitWithItemsDTO getUserOutfitById(final UUID userId, final UUID outfitId) {

        final OutfitEntity outfitEntity = findOutfitByID(outfitId);

        if (!outfitEntity.getUser().getId().equals(userId)) {
            throw new OutfitWrongUserException(userId, outfitId);
        }


        final List<ClothingItemEntity> clothingItems = outfitItemsService.getClothingItemsByOutfitId(outfitId);
        final List<AccessoryEntity> accessories = outfitItemsService.getAccessoriesByOutfitId(outfitId);

        return new OutfitWithItemsDTO(outfitId, outfitEntity.getName(), outfitEntity.getSeason(),
                outfitEntity.getStyle(), outfitEntity.getPhotoUrl(), clothingItems, accessories);
    }

    public OutfitWithItemsDTO addUserOutfit(final UUID userId, final OutfitInputDto outfitInputDto) {
        final OutfitEntity outfitEntity =
                new OutfitEntity(findUserByID(userId), outfitInputDto.name(), outfitInputDto.style(),
                        outfitInputDto.season(), outfitInputDto.photoUrl());
        outfitRepository.save(outfitEntity);

        for (final UUID clothingItemId: outfitInputDto.clothingItemsId()) {
            outfitItemsService.addItemToOutfit(outfitEntity, clothingItemId, null, userId);
        }

        for (final UUID accessoryId: outfitInputDto.accessoriesId()) {
            outfitItemsService.addItemToOutfit(outfitEntity, null, accessoryId, userId);
        }

        final List<ClothingItemEntity> clothingItems = outfitItemsService
                .getClothingItemsByOutfitId(outfitEntity.getId());
        final List<AccessoryEntity> accessories = outfitItemsService.getAccessoriesByOutfitId(outfitEntity.getId());

        return new OutfitWithItemsDTO(outfitEntity.getId(), outfitEntity.getName(), outfitEntity.getSeason(),
                outfitEntity.getStyle(), outfitEntity.getPhotoUrl(), clothingItems, accessories);
    }

    public void deleteOutfit(final UUID userId, final UUID outfitId) {
        final OutfitEntity outfitEntity = findOutfitByID(outfitId);

        if (!outfitEntity.getUser().getId().equals(userId)) {
            throw new AccessoryWrongUserException(userId, outfitId);
        }

        outfitItemsService.deleteOutfitItemsByOutfitId(outfitEntity.getId());
        outfitRepository.deleteById(outfitEntity.getId());
    }

    public OutfitWithItemsDTO updateUserOutfit(final UUID userId, final UUID outfitId,
                                         final OutfitInputDto outfitInputDto) {

        final OutfitEntity oldOutfitEntity = findOutfitByID(outfitId);

        if (!oldOutfitEntity.getUser().getId().equals(userId)) {
            throw new OutfitWrongUserException(userId, oldOutfitEntity.getId());
        }

        if (outfitInputDto.name() != null) {
            oldOutfitEntity.setName(outfitInputDto.name());
        }
        if (outfitInputDto.style() != null) {
            oldOutfitEntity.setStyle(outfitInputDto.style());
        }
        if (outfitInputDto.season() != null) {
            oldOutfitEntity.setSeason(outfitInputDto.season());
        }
        if (outfitInputDto.photoUrl() != null) {
            oldOutfitEntity.setPhotoUrl(outfitInputDto.photoUrl());
        }

        outfitItemsService.replaceOutfitItemsByOutfitId(oldOutfitEntity, outfitInputDto.clothingItemsId(),
                outfitInputDto.accessoriesId(), userId);

        final List<ClothingItemEntity> clothingItems = outfitItemsService
                .getClothingItemsByOutfitId(oldOutfitEntity.getId());
        final List<AccessoryEntity> accessories = outfitItemsService.getAccessoriesByOutfitId(oldOutfitEntity.getId());

        return new OutfitWithItemsDTO(oldOutfitEntity.getId(), oldOutfitEntity.getName(), oldOutfitEntity.getSeason(),
                oldOutfitEntity.getStyle(), oldOutfitEntity.getPhotoUrl(), clothingItems, accessories);
    }

    UserEntity findUserByID(final UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    OutfitEntity findOutfitByID(final UUID outfitId) {
        return outfitRepository.findById(outfitId).orElseThrow(() -> new OutfitNotFoundException(outfitId));
    }



}
