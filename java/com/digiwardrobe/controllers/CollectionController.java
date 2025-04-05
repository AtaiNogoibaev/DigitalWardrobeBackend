package com.digiwardrobe.controllers;

import com.digiwardrobe.configurations.utils.AuthUtils;
import com.digiwardrobe.data_access.entity.CollectionEntity;
import com.digiwardrobe.model.CollectionDTO;
import com.digiwardrobe.model.CollectionInputDTO;
import com.digiwardrobe.services.CollectionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(final CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @QueryMapping
    public List<CollectionEntity> userCollections() {
        return collectionService.getAllCollections(getUserId());
    }

    @QueryMapping
    public CollectionDTO userCollection(@Argument final UUID collectionId) {
        return collectionService.getCollection(getUserId(), collectionId);
    }

    @MutationMapping
    public CollectionDTO createCollection(@Argument final CollectionInputDTO collection) {
        return collectionService.createCollection(getUserId(), collection);
    }

    @MutationMapping
    public CollectionDTO updateCollection(@Argument final UUID collectionId,
                                          @Argument final CollectionInputDTO collection) {
        return collectionService.updateCollection(getUserId(), collectionId, collection);
    }

    @MutationMapping
    public boolean deleteCollection(@Argument final UUID collectionId) {
        collectionService.deleteCollection(getUserId(), collectionId);
        return true;
    }

    private static UUID getUserId() {
        return AuthUtils.getCurrentUserId();
    }
}
