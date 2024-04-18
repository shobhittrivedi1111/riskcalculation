package com.nagarro.riskcalculation.controller;

import com.nagarro.riskcalculation.model.RiskScoreLevel;
import com.nagarro.riskcalculation.service.RiskScoreLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/risk-score-levels")
public class RiskScoreLevelController {
    @Autowired
    private RiskScoreLevelService riskScoreLevelService;


    @GetMapping
    public ResponseEntity<List<RiskScoreLevel>> getAllRiskScoreLevels() {
        log.info("Retrieved all Risk Score Levels: {}");
        return new ResponseEntity<>(riskScoreLevelService.getAllRiskScoreLevels(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskScoreLevel> getRiskScoreLevelById(@PathVariable int id) {
        log.info("Retrieved Risk Score Level: {}");
        return new ResponseEntity<>(riskScoreLevelService.getRiskScoreLevelById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RiskScoreLevel> createRiskScoreLevel(@RequestBody RiskScoreLevel riskScoreLevel) {
        log.info("Created Risk Score Level: {}", riskScoreLevel);
        return new ResponseEntity<>(riskScoreLevelService.createRiskScoreLevel(riskScoreLevel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskScoreLevel> updateRiskScoreLevel(@PathVariable int id, @RequestBody RiskScoreLevel riskScoreLevel) {
        log.info("Updating  Risk Score Level: {}", riskScoreLevel);
        return new ResponseEntity<>(riskScoreLevelService.updateRiskScoreLevel(id, riskScoreLevel), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRiskScoreLevel(@PathVariable int id) {
        log.info("Deleting Risk Score Level: {}", id);
        return new ResponseEntity<>(riskScoreLevelService.deleteRiskScoreLevel(id), HttpStatus.NO_CONTENT);
    }
    @GetMapping("/risk-levels")
    public ResponseEntity<List<String>> getAllRiskLevelNames() {
        List<String> riskLevelNames = riskScoreLevelService.getAllLevelNames();
        if (riskLevelNames.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(riskLevelNames, HttpStatus.OK);
    }
}
