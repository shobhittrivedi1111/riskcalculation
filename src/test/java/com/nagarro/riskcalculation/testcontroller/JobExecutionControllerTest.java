package com.nagarro.riskcalculation.testcontroller;
import com.nagarro.riskcalculation.controller.JobExecutionController;
import com.nagarro.riskcalculation.model.JobExecutionDTO;
import com.nagarro.riskcalculation.service.JobExecutionService;
import com.nagarro.riskcalculation.service.FormulaEvaluatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
class JobExecutionControllerTest {

    @Mock
    private JobExecutionService jobExecutionService;

    @Mock
    private FormulaEvaluatorService formulaEvaluatorService;

    @InjectMocks
    private JobExecutionController jobExecutionController;



    @Test
    void testTriggerJobExecution() {
        // Mock data
        List<JobExecutionDTO> jobExecutionDTOS = new ArrayList<>();
        jobExecutionDTOS.add(new JobExecutionDTO());
        jobExecutionDTOS.add(new JobExecutionDTO());
        when(jobExecutionService.getAllJobExecutions()).thenReturn(jobExecutionDTOS);

        // Execute the method
        ResponseEntity<List<JobExecutionDTO>> response = jobExecutionController.triggerJobExecution();

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jobExecutionDTOS, response.getBody());
        verify(formulaEvaluatorService, times(1)).evaluateFormulas("Trigger");
        verify(jobExecutionService, times(1)).getAllJobExecutions();
    }
}
