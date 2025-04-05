package com.digiwardrobe.data_access.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digiwardrobe.data_access.entity.ClothingFitEntity;

public interface ClothingFitRepository extends JpaRepository<ClothingFitEntity, Integer> {
}