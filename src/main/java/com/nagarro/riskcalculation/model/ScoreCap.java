package com.nagarro.riskcalculation.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Score_Cap",indexes = {
        @Index(name = "idx_id", columnList = "id"),
        @Index(name = "idx_risk_score_level", columnList = "riskscorelevel"),
        @Index(name = "idx_conditionValue", columnList = "conditionValue"),
        @Index(name = "idx_total_risk_capped_score", columnList = "total_risk_capped_score",unique = true)
       })
public class ScoreCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "riskscorelevel", referencedColumnName = "level")
    private RiskScoreLevel riskScoreLevel;

    @NonNull
    @Min(value = 1, message = "ConditionValue must be greater than or equal to 0.")
    @Max(value = 100, message = "ConditionValue must be less than or equal to 100.")
    private Integer conditionValue;

   // @Column(nullable = false)
    @Min(value = 1, message = "Total risk capped score must be greater than or equal to 0.")
    @Max(value = 100, message = "Total risk capped score must be less than or equal to 100.")
    @Column(nullable = false, columnDefinition = "DECIMAL(5,2) CHECK (total_risk_capped_score >= 1 AND total_risk_capped_score <= 100)")
    private Double total_risk_capped_score;
}
