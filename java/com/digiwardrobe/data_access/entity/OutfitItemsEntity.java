package com.digiwardrobe.data_access.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;


import java.util.UUID;

@Entity
@Table(name = "outfit_items")
public class OutfitItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "outfit_id")
    private OutfitEntity outfit;

    @ManyToOne
    @JoinColumn(name = "clothingitem_id")
    private ClothingItemEntity clothingItem;

    @ManyToOne
    @JoinColumn(name = "accessory_id")
    private AccessoryEntity accessory;

    public UUID getId() {
        return id;
    }

    public OutfitEntity getOutfit() {
        return outfit;
    }

    public ClothingItemEntity getClothingItem() {
        return clothingItem;
    }

    public AccessoryEntity getAccessory() {
        return accessory;
    }

    public void setOutfit(OutfitEntity outfit) {
        this.outfit = outfit;
    }

    public void setClothingItem(ClothingItemEntity clothingItem) {
        this.clothingItem = clothingItem;
    }

    public void setAccessory(AccessoryEntity accessory) {
        this.accessory = accessory;
    }
}