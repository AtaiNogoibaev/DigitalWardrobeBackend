package com.digiwardrobe.services;

import com.digiwardrobe.data_access.entity.AccessoryTypeEntity;
import com.digiwardrobe.data_access.repositories.AccessoryTypeRepository;
import com.digiwardrobe.exceptions.AccessoryTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessoryTypeService {

    @Autowired
    private final AccessoryTypeRepository accessoryTypeRepository;

    public AccessoryTypeService(final AccessoryTypeRepository accessoryTypeRepository) {
        this.accessoryTypeRepository = accessoryTypeRepository;
    }

    public AccessoryTypeEntity getAccessoryTypeById(final int accessoryTypeId) {
        return accessoryTypeRepository.findById(accessoryTypeId)
                .orElseThrow(() -> new AccessoryTypeNotFoundException(accessoryTypeId));
    }
}
