package com.megaventory.project.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int RelationshipId;

    @Column
    private Long productSKU;

    @Column
    private String clientEmail;

    @Column
    private String supplierEmail;
}
