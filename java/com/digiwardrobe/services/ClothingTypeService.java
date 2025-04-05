package com.digiwardrobe.services;

import com.digiwardrobe.data_access.entity.ClothingTypeEntity;
import com.digiwardrobe.data_access.repositories.ClothingTypeRepository;
import com.digiwardrobe.exceptions.ClothingTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClothingTypeService {

    @Autowired
    private final ClothingTypeRepository clothingTypeRepository;

    public ClothingTypeService(final ClothingTypeRepository clothingTypeRepository) {
        this.clothingTypeRepository = clothingTypeRepository;
    }

    public ClothingTypeEntity getClothingType(final int clothingTypeId) {
        return clothingTypeRepository.findById(clothingTypeId)
                .orElseThrow(() -> new ClothingTypeNotFoundException(clothingTypeId));
    }
}
