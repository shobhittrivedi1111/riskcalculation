package com.nagarro.riskcalculation.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Risk_Score_Level",
        indexes = {
                @Index(name = "idx_id", columnList = "id"),
                @Index(name = "idx_min_score", columnList = "minScore" ,unique = true),
                @Index(name = "idx_max_score", columnList = "maxScore", unique = true)
        })
public class RiskScoreLevel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String level;
    @Min(value = 1, message = "The 'minscore' field must be greater than or equal to 1.")
    @Max(value = 100, message = "The 'maxscore' field must be less than or equal to 100.")
    @Column(nullable = false, unique = true)
    private double minScore;
    @Min(value = 1, message = "The 'minscore' field must be greater than or equal to 1.")
    @Max(value = 100, message = "The 'maxscore' field must be less than or equal to 100.")
    @Column(nullable = false, unique = true)
    private double maxScore;
}


