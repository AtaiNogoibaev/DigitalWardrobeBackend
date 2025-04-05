package com.digiwardrobe.data_access.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digiwardrobe.data_access.entity.OutfitEntity;

public interface OutfitRepository extends JpaRepository<OutfitEntity, UUID> {
    List<OutfitEntity> findAllByUserId(UUID userId);
}
