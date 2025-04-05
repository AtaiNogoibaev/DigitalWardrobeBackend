package com.digiwardrobe.data_access.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digiwardrobe.data_access.entity.ClothingTypeEntity;

public interface ClothingTypeRepository extends JpaRepository<ClothingTypeEntity, Integer> {
}