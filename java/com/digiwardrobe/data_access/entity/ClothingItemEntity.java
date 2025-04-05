package com.digiwardrobe.data_access.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;


@Entity
@Table(name = "clothing_items")
public class ClothingItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity user;

    private String size;
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ClothingTypeEntity clothingType;

    @ManyToOne
    @JoinColumn(name = "fit_id", nullable = false)
    private ClothingFitEntity clothingFit;

    private String color;
    private String brand;
    @Column(name = "photo_url")
    private String photoUrl;

    public ClothingItemEntity() {
    }

    public ClothingItemEntity(UserEntity user, String size, String name, ClothingTypeEntity clothingType,
                              ClothingFitEntity clothingFit, String color, String brand, String photoUrl) {
        this.user = user;
        this.size = size;
        this.name = name;
        this.clothingType = clothingType;
        this.clothingFit = clothingFit;
        this.color = color;
        this.brand = brand;
        this.photoUrl = photoUrl;
    }

    public UUID getId() {
        return id;
    }

    @JsonProperty("user_id")
    public UUID getUserId() {
        return user != null ? user.getId() : null;
    }

    public String getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public ClothingTypeEntity getClothingType() {
        return clothingType;
    }

    public void setClothingType(ClothingTypeEntity clothingType) {
        this.clothingType = clothingType;
    }

    public ClothingFitEntity getClothingFit() {
        return clothingFit;
    }

    public void setClothingFit(ClothingFitEntity clothingFit) {
        this.clothingFit = clothingFit;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}