package com.nagarro.riskcalculation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "output", indexes = {
        @Index(name = "idx_company_name", columnList = "companyName")
})
public class Output {
    @Id
    private String companyName;
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "formula_attribute")
    @Column(name = "formula_value")
    @CollectionTable(name = "formula_output",
            joinColumns = @JoinColumn(name = "companyName"),
            indexes = {
                    @Index(name = "idx_formula_attribute", columnList = "formula_attribute")
            }
    )
    private Map<String,String> formulaOutput;
}
