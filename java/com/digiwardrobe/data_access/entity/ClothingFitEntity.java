package com.digiwardrobe.data_access.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "clothing_fit")
public class ClothingFitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fit_value", unique = true, nullable = false)
    private String fitValue;

    public ClothingFitEntity() {

    }

    public ClothingFitEntity(String fitValue) {
        this.fitValue = fitValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFitValue() {
        return fitValue;
    }

    public void setFitValue(String fitValue) {
        this.fitValue = fitValue;
    }
}