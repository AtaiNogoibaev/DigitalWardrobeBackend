package com.digiwardrobe.data_access.repositories;

import com.digiwardrobe.data_access.entity.CollectionEntity;
import com.digiwardrobe.data_access.entity.CollectionOutfitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionOutfitsRepository extends JpaRepository<CollectionOutfitsEntity, UUID> {

    void deleteByCollection(CollectionEntity collectionEntity);

    List<CollectionOutfitsEntity> getCollectionOutfitsByCollection(CollectionEntity collectionEntity);

}
