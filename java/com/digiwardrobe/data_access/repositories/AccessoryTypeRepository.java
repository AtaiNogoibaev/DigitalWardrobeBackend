package com.digiwardrobe.data_access.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digiwardrobe.data_access.entity.AccessoryTypeEntity;

public interface AccessoryTypeRepository extends JpaRepository<AccessoryTypeEntity, Integer> {
}