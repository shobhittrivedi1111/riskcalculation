package com.nagarro.riskcalculation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobExecutionResponse {
    private String message;

    public JobExecutionResponse(String message) {
        this.message = message;
    }

    // Add getters and setters for the 'message' property
}
