package com.nagarro.riskcalculation.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "job_execution", indexes = {
        @Index(name = "idx_id", columnList = "id"),
        @Index(name = "idx_timestamp", columnList = "timestamp"),
        @Index(name = "idx_failed_companies", columnList = "failed_Companies"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_job_status_name", columnList = "job_status_name")
})
@NoArgsConstructor
@AllArgsConstructor
public class JobExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "failed_companies")
    private String failedCompanies;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private JobStatus status;

   @Column(name= "job_status_name")
    private String jobStatusName;
    // Constructors, getters, and setters
}



