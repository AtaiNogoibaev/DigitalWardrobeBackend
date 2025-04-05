package com.digiwardrobe.data_access.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;

import java.util.UUID;


@Entity
@Table(name = "accessories")
public class AccessoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private AccessoryTypeEntity accessoryType;

    private String color;
    private String brand;

    @Column(name = "photo_url")
    private String photoUrl;
    private String name;

    public AccessoryEntity() {

    }

    public AccessoryEntity(UserEntity user, AccessoryTypeEntity accessoryType,
                           String color, String brand, String photoUrl, String name) {
        this.user = user;
        this.accessoryType = accessoryType;
        this.color = color;
        this.brand = brand;
        this.photoUrl = photoUrl;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public AccessoryTypeEntity getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(AccessoryTypeEntity accessoryType) {
        this.accessoryType = accessoryType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}