package com.nagarro.riskcalculation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Risk_Dimension", indexes = {
        @Index(name = "idx_dimension", columnList = "dimension"),
        @Index(name = "idx_weight", columnList = "weight")
})
public class RiskDimension {
    @Id
    @Column(nullable = false, length = 100, updatable = false) // Change the length as per your requirement
    private String dimension;

 @Column(nullable = false, columnDefinition = "DECIMAL(3,2) CHECK (weight > 0.00 AND weight <= 1.0)")
    private Double weight;

}

