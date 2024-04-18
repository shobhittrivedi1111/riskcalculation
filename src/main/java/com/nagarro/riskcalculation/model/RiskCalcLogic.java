package com.nagarro.riskcalculation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Risk_Calc_Logic", indexes = {
        @Index(name = "idx_id", columnList = "id"),
        @Index(name = "idx_element_name", columnList = "elementName"),
        @Index(name = "idx_formula", columnList = "formula")
})
public class RiskCalcLogic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Column(unique = true)
    private String elementName;
    @Column (nullable = false)
    private String formula;

}
