package com.digiwardrobe.data_access.entity;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "collection_outfits")
public class CollectionOutfitsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private CollectionEntity collection;

    @ManyToOne
    @JoinColumn(name = "outfit_id", nullable = false)
    private OutfitEntity outfit;

    public CollectionOutfitsEntity() {

    }

    public CollectionOutfitsEntity(CollectionEntity collection, OutfitEntity outfit) {
        this.collection = collection;
        this.outfit = outfit;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CollectionEntity getCollection() {
        return collection;
    }

    public void setCollection(CollectionEntity collection) {
        this.collection = collection;
    }

    public OutfitEntity getOutfit() {
        return outfit;
    }

    public void setOutfit(OutfitEntity outfit) {
        this.outfit = outfit;
    }
}