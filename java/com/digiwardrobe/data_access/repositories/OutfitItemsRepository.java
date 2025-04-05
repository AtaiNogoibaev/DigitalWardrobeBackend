package com.digiwardrobe.data_access.repositories;

import com.digiwardrobe.data_access.entity.OutfitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository; // Important to add this

import com.digiwardrobe.data_access.entity.OutfitItemsEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutfitItemsRepository extends JpaRepository<OutfitItemsEntity, UUID> {

    void deleteByOutfit(OutfitEntity outfit);

    @Query("SELECT oi FROM OutfitItemsEntity oi WHERE oi.outfit.id = :outfitId AND oi.clothingItem IS NOT NULL")
    List<OutfitItemsEntity> findClothingItemsByOutfitId(UUID outfitId);

    @Query("SELECT oi FROM OutfitItemsEntity oi WHERE oi.outfit.id = :outfitId AND oi.accessory IS NOT NULL")
    List<OutfitItemsEntity> findAccessoriesByOutfitId(UUID outfitId);
}