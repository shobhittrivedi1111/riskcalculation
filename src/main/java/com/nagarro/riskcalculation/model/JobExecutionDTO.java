package com.nagarro.riskcalculation.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobExecutionDTO {
    private Long id;
    private LocalDateTime timestamp;
    private String failedCompanies;
    private JobStatus status;
    private String jobStatusName;

    // Constructors, getters, and setters
}

