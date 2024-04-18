package com.nagarro.riskcalculation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SchedulerService {
    @Autowired
    FormulaEvaluatorService formulaEvaluatorService;
    @Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 1 * 60 * 1000)
    public void evaluateFormulasByScheduling(){
        log.info("Formula Evaluating using Scheduler");
        formulaEvaluatorService.evaluateFormulas("Scheduler");
    }

}
