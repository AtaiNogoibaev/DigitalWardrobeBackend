package com.digiwardrobe.services;

import com.digiwardrobe.data_access.entity.CollectionEntity;
import com.digiwardrobe.data_access.entity.CollectionOutfitsEntity;
import com.digiwardrobe.data_access.entity.OutfitEntity;
import com.digiwardrobe.data_access.repositories.CollectionOutfitsRepository;
import com.digiwardrobe.data_access.repositories.CollectionRepository;
import com.digiwardrobe.data_access.repositories.OutfitRepository;
import com.digiwardrobe.exceptions.CollectionNotFoundException;
import com.digiwardrobe.exceptions.CollectionWrongUserException;
import com.digiwardrobe.exceptions.OutfitNotFoundException;
import com.digiwardrobe.exceptions.OutfitWrongUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionOutfitsService {

    private final CollectionOutfitsRepository collectionOutfitsRepository;
    private final CollectionRepository collectionRepository;
    private final OutfitRepository outfitRepository;

    public CollectionOutfitsService(final CollectionOutfitsRepository collectionOutfitsRepository,
                                    final CollectionRepository collectionRepository,
                                    final OutfitRepository outfitRepository) {
        this.collectionOutfitsRepository = collectionOutfitsRepository;
        this.collectionRepository = collectionRepository;
        this.outfitRepository = outfitRepository;
    }


    @Transactional
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void insertOutfitsIntoCollection(final UUID userId,
                                            final UUID collectionId,
                                            final List<UUID> outfitIds) {

        final CollectionEntity collectionEntity = findCollectionEntity(collectionId);

        if (!collectionEntity.getUser().getId().equals(userId)) {
            throw new CollectionNotFoundException(collectionId);
        }

        for (final UUID outfitId : outfitIds) {
            if (outfitRepository.existsById(outfitId)) {
                final OutfitEntity outfitEntity = outfitRepository.findById(outfitId)
                        .orElseThrow(() -> new OutfitNotFoundException(outfitId));
                if (outfitEntity.getUser().getId().equals(userId)) {
                    collectionOutfitsRepository.save(new CollectionOutfitsEntity(collectionEntity, outfitEntity));
                } else {
                    throw new OutfitWrongUserException(userId, outfitId);
                }
            }
        }
    }

    @Transactional
    public void replaceOutfitsInCollection (final UUID userId,
                                            final UUID collectionId,
                                            final List<UUID> outfitIds) {

        final CollectionEntity collectionEntity = findCollectionEntity(collectionId);

        if (!collectionEntity.getUser().getId().equals(userId)) {
            throw new CollectionWrongUserException(userId, collectionId);
        }

        deleteOutfitsInCollection(userId, collectionId);

        insertOutfitsIntoCollection(userId, collectionId, outfitIds);

    }

    @Transactional
    public void deleteOutfitsInCollection (final UUID userId,
                                           final UUID collectionId) {

        final CollectionEntity collectionEntity = findCollectionEntity(collectionId);

        if (!collectionEntity.getUser().getId().equals(userId)) {
            throw new CollectionWrongUserException(userId, collectionId);
        }

        collectionOutfitsRepository.deleteByCollection(collectionEntity);
    }

    @Transactional(readOnly = true)
    public List<CollectionOutfitsEntity> getCollectionOutfitEntities(final UUID userId,
                                                                     final UUID collectionId) {

        final CollectionEntity collectionEntity = findCollectionEntity(collectionId);

        if (!collectionEntity.getUser().getId().equals(userId)) {
            throw new CollectionWrongUserException(userId, collectionId);
        }

        return collectionOutfitsRepository.getCollectionOutfitsByCollection(collectionEntity);
    }

    private CollectionEntity findCollectionEntity(final UUID collectionId) {
        return collectionRepository.findById(collectionId)
                .orElseThrow(() -> new CollectionNotFoundException(collectionId));
    }
}