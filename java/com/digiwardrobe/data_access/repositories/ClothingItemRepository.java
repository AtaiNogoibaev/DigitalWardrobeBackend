package com.digiwardrobe.data_access.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digiwardrobe.data_access.entity.ClothingItemEntity;

public interface ClothingItemRepository extends JpaRepository<ClothingItemEntity, UUID> {
    List<ClothingItemEntity> findAllByUser_Id(UUID userId);

    void deleteById(UUID id);
}