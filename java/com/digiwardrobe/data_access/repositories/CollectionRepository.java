package com.digiwardrobe.data_access.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.digiwardrobe.data_access.entity.CollectionEntity;

public interface CollectionRepository extends JpaRepository<CollectionEntity, UUID> {
    List<CollectionEntity> findAllByUser_Id(UUID userId);
}