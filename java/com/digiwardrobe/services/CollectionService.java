package com.digiwardrobe.services;

import com.digiwardrobe.data_access.entity.CollectionEntity;
import com.digiwardrobe.data_access.entity.UserEntity;
import com.digiwardrobe.data_access.repositories.CollectionRepository;
import com.digiwardrobe.data_access.repositories.UserRepository;
import com.digiwardrobe.exceptions.CollectionNotFoundException;
import com.digiwardrobe.exceptions.CollectionWrongUserException;
import com.digiwardrobe.exceptions.UserNotFoundException;
import com.digiwardrobe.model.CollectionDTO;
import com.digiwardrobe.model.CollectionInputDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionOutfitsService collectionOutfitsService;
    private final UserRepository userRepository;

    public CollectionService(final CollectionRepository collectionRepository,
                             final CollectionOutfitsService collectionOutfitsService,
                             final UserRepository userRepository) {
        this.collectionRepository = collectionRepository;
        this.collectionOutfitsService = collectionOutfitsService;
        this.userRepository = userRepository;
    }

    public CollectionDTO createCollection(final UUID userId,
                                          final CollectionInputDTO collection) {

        final CollectionEntity collectionEntity = new CollectionEntity(findUser(userId), collection.name());

        collectionRepository.save(collectionEntity);

        collectionOutfitsService.insertOutfitsIntoCollection(userId,
                collectionEntity.getId(),
                collection.outfitIds());

        return new CollectionDTO(collectionEntity.getId(), collectionEntity.getUser().getId(),
                collectionEntity.getName(), collectionOutfitsService
                .getCollectionOutfitEntities(userId, collectionEntity.getId())
                .stream().map(c -> c.getOutfit().getId()).toList());
    }

    public List<CollectionEntity> getAllCollections(final UUID userId) {
        return collectionRepository.findAllByUser_Id(userId);
    }

    public CollectionDTO getCollection(final UUID userId, final UUID collectionId) {
        final CollectionEntity collectionEntity = findCollection(userId, collectionId);

        return new CollectionDTO(collectionEntity.getId(), collectionEntity.getUser().getId(),
                collectionEntity.getName(), collectionOutfitsService
                .getCollectionOutfitEntities(userId, collectionEntity.getId())
                .stream().map(c -> c.getOutfit().getId()).toList());
    }

    public CollectionDTO updateCollection(final UUID userId,
                                          final UUID collectionId,
                                          final CollectionInputDTO collection) {

        final CollectionEntity oldCollectionEntity = findCollection(userId, collectionId);

        if (collection.name() != null) {
            oldCollectionEntity.setName(collection.name());
        }
        collectionRepository.save(oldCollectionEntity);

        collectionOutfitsService.replaceOutfitsInCollection(userId, collectionId, collection.outfitIds());

        return new CollectionDTO(oldCollectionEntity.getId(), oldCollectionEntity.getUser().getId(),
                oldCollectionEntity.getName(), collectionOutfitsService
                .getCollectionOutfitEntities(userId, oldCollectionEntity.getId())
                .stream().map(c -> c.getOutfit().getId()).toList());
    }

    public void deleteCollection(final UUID userId, final UUID collectionId) {
        collectionRepository.delete(findCollection(userId, collectionId));
    }

    UserEntity findUser(final UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    CollectionEntity findCollection(final UUID userId, final UUID collectionId) {
        final CollectionEntity collectionEntity = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new CollectionNotFoundException(collectionId));

        if (!collectionEntity.getUser().getId().equals(userId)) {
            throw new CollectionWrongUserException(userId, collectionId);
        }

        return collectionEntity;
    }
}
