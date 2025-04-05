package com.digiwardrobe.services;

import com.digiwardrobe.data_access.entity.AccessoryEntity;
import com.digiwardrobe.data_access.entity.UserEntity;
import com.digiwardrobe.data_access.repositories.AccessoryRepository;
import com.digiwardrobe.data_access.repositories.UserRepository;
import com.digiwardrobe.exceptions.AccessoryNotFoundException;
import com.digiwardrobe.exceptions.AccessoryWrongUserException;
import com.digiwardrobe.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final UserRepository userRepository;

    public AccessoryService(final AccessoryRepository accessoryRepository,
                            final UserRepository userRepository) {
        this.accessoryRepository = accessoryRepository;
        this.userRepository = userRepository;
    }

    public List<AccessoryEntity> getAllAccessory(final UUID userId) {
        return accessoryRepository.findAllByUser_Id(userId);
    }

    public AccessoryEntity getAccessoryById(final UUID userId, final UUID accessoryId) {
        final AccessoryEntity accessory = accessoryRepository.findById(accessoryId)
                .orElseThrow(() -> new AccessoryNotFoundException(accessoryId));

        if (!accessory.getUser().getId().equals(userId)) {
            throw new AccessoryWrongUserException(userId, accessoryId);
        }

        return accessory;
    }

    public AccessoryEntity addAccessory(final UUID userId, final AccessoryEntity accessoryEntity) {
        final UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        accessoryEntity.setUser(user);
        return accessoryRepository.save(accessoryEntity);
    }

    public void deleteAccessory(final UUID userId, final UUID accessoryId) {
        final AccessoryEntity accessory = accessoryRepository.findById(accessoryId)
                .orElseThrow(() -> new AccessoryNotFoundException(accessoryId));

        if (!accessory.getUser().getId().equals(userId)) {
            throw new AccessoryWrongUserException(userId, accessoryId);
        }

        accessoryRepository.deleteById(accessoryId);
    }


    public AccessoryEntity updateAccessory(final UUID userId,
                                           final UUID accessoryId,
                                           final AccessoryEntity accessoryEntity) {
        final AccessoryEntity oldAccessory = getAccessoryById(userId, accessoryId);

        if (accessoryEntity.getName() != null) {
            oldAccessory.setName(accessoryEntity.getName());
        }

        if (accessoryEntity.getBrand() != null) {
            oldAccessory.setBrand(accessoryEntity.getBrand());
        }

        if (accessoryEntity.getColor() != null) {
            oldAccessory.setColor(accessoryEntity.getColor());
        }

        if (accessoryEntity.getPhotoUrl() != null) {
            oldAccessory.setPhotoUrl(accessoryEntity.getPhotoUrl());
        }

        if (accessoryEntity.getAccessoryType() != null) {
            oldAccessory.setAccessoryType(accessoryEntity.getAccessoryType());
        }

        return accessoryRepository.save(oldAccessory);

    }


}
