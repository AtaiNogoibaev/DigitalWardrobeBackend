package com.digiwardrobe.services;

import com.digiwardrobe.data_access.entity.ClothingFitEntity;
import com.digiwardrobe.data_access.repositories.ClothingFitRepository;
import com.digiwardrobe.exceptions.ClothingFitNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClothingFitService {

    @Autowired
    private final ClothingFitRepository clothingFitRepository;

    public ClothingFitService(final ClothingFitRepository clothingFitRepository) {
        this.clothingFitRepository = clothingFitRepository;
    }

    public ClothingFitEntity getClothingFit(final int clothingFitId) {
        return clothingFitRepository.findById(clothingFitId)
                .orElseThrow(() -> new ClothingFitNotFoundException(clothingFitId));

    }

}
