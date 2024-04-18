package com.nagarro.riskcalculation.controller;

import com.nagarro.riskcalculation.model.RiskCalcLogic;
import com.nagarro.riskcalculation.service.RiskCalcLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/risk-calc-logics")
public class RiskCalcLogicController {
    @Autowired
    private RiskCalcLogicService riskCalcLogicService;

    @GetMapping
    public ResponseEntity<List<RiskCalcLogic>> getAllRiskCalcLogics() {
        log.info("Getting all RiskCalcLogics");
        return new ResponseEntity<>(riskCalcLogicService.getAllRiskCalcLogics(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskCalcLogic> getRiskCalcLogicById(@PathVariable int id) {
        log.info("Getting RiskCalcLogic by ID: {}", id);
        return new ResponseEntity<>(riskCalcLogicService.getRiskCalcLogicById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RiskCalcLogic> createRiskCalcLogic(@RequestBody RiskCalcLogic riskCalcLogic) {
        log.info("Creating RiskCalcLogic: {}", riskCalcLogic);
        return new ResponseEntity<>(riskCalcLogicService.createRiskCalcLogic(riskCalcLogic), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskCalcLogic> updateRiskCalcLogic(@PathVariable int id, @RequestBody RiskCalcLogic updatedRiskCalcLogic) {
        log.info("Updating RiskCalcLogic with ID: {}", id);
        return new ResponseEntity<>(riskCalcLogicService.updateRiskCalcLogic(id, updatedRiskCalcLogic), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRiskCalcLogic(@PathVariable int id) {
        log.info("Deleting RiskCalcLogic with ID: {}", id);
        return new ResponseEntity<>(riskCalcLogicService.deleteRiskCalcLogic(id), HttpStatus.NO_CONTENT);
    }
    @GetMapping("/elementNames")
    public ResponseEntity<List<String>> getAllElementNames() {
        List<String> elementNames = riskCalcLogicService.getAllElementNames();
        if (elementNames.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(elementNames);
    }




















   /* @Autowired
    private RiskCalcLogicService riskCalcLogicService;
    @GetMapping
    public List<RiskCalcLogic> getAllRiskCalcLogics() {
        return riskCalcLogicService.getAllRiskCalcLogics();
    }

    @GetMapping("/{id}")
    public RiskCalcLogic getRiskCalcLogicById(@PathVariable int id) {
        return riskCalcLogicService.getRiskCalcLogicById(id);
    }

    @PostMapping
    public RiskCalcLogic createRiskCalcLogic(@RequestBody RiskCalcLogic riskCalcLogic) {
        return riskCalcLogicService.createRiskCalcLogic(riskCalcLogic);
    }

    @PutMapping("/{id}")
    public RiskCalcLogic updateRiskCalcLogic(@PathVariable int id, @RequestBody RiskCalcLogic updatedRiskCalcLogic) {
        return riskCalcLogicService.updateRiskCalcLogic(id, updatedRiskCalcLogic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRiskCalcLogic(@PathVariable int id) {
        riskCalcLogicService.deleteRiskCalcLogic(id);
        return ResponseEntity.noContent().build();
    }*/
}

