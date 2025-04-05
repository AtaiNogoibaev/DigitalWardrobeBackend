package com.digiwardrobe.data_access.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digiwardrobe.data_access.entity.AccessoryEntity;

public interface AccessoryRepository extends JpaRepository<AccessoryEntity, UUID> {
    List<AccessoryEntity> findAllByUser_Id(UUID userId);
}