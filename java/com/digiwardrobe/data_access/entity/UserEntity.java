package com.digiwardrobe.data_access.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String hashedPassword;

    @OneToMany(mappedBy = "user")
    private List<OutfitEntity> outfits = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CollectionEntity> collections = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(String email, String hashedPassword) {
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    UserEntity(UUID id, String email, String hashedPassword) {
        this.id = id;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<OutfitEntity> getOutfits() {
        return outfits;
    }

    public void setOutfits(List<OutfitEntity> outfits) {
        this.outfits = outfits;
    }

    public List<CollectionEntity> getCollections() {
        return collections;
    }

    public void setCollections(List<CollectionEntity> collections) {
        this.collections = collections;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.hashedPassword = password;
    }
}
