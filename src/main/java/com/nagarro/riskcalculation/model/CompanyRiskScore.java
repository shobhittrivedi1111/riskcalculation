package com.nagarro.riskcalculation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Company_Risk_Score", indexes = {
        @Index(name = "idx_company_name", columnList = "companyName",unique = true)})
public class CompanyRiskScore {
    @Id
    @Column(unique = true)
    @NotNull(message = "Company name cannot be null")
    @NotBlank(message = "Company name cannot be blank")
    private String companyName;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "attribute_name")
    @Column(name = "attribute_value",
            columnDefinition = "DECIMAL(5,2) CHECK (attribute_value >=1 AND attribute_value <= 100)")
//    @CollectionTable(name = "dimensions_attribute", joinColumns = @JoinColumn(name = "companyName"))
    @CollectionTable(name = "dimensions_attribute", joinColumns = @JoinColumn(name = "companyName"),
            indexes = {
                    @Index(name = "idx_attribute_name", columnList = "attribute_name"),
                    @Index(name = "idx_attribute_value", columnList = "attribute_value")
            }
    )
    @NotNull(message = "Dimensions map cannot be null")
    private Map<String, String> dimensions;


}

