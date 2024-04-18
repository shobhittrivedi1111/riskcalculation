package com.nagarro.riskcalculation.controller;

import com.nagarro.riskcalculation.model.JobExecution;
import com.nagarro.riskcalculation.model.JobExecutionDTO;
import com.nagarro.riskcalculation.model.JobExecutionResponse;
import com.nagarro.riskcalculation.service.FormulaEvaluatorService;
import com.nagarro.riskcalculation.service.JobExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-execution")
public class JobExecutionController {
    @Autowired
    private JobExecutionService jobExecutionService;
    @Autowired
    private FormulaEvaluatorService formulaEvaluatorService;


    @GetMapping("/trigger")
   public ResponseEntity<List<JobExecutionDTO> >triggerJobExecution() {
    formulaEvaluatorService.evaluateFormulas("Trigger");
    List<JobExecutionDTO> jobExecutiondtos = jobExecutionService.getAllJobExecutions();
    return ResponseEntity.ok(jobExecutiondtos);
    }
}

