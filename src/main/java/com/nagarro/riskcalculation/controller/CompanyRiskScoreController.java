package com.nagarro.riskcalculation.controller;

import com.nagarro.riskcalculation.model.CompanyRiskScore;
import com.nagarro.riskcalculation.service.CompanyRiskScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/companyRiskScore")
@Slf4j
public class CompanyRiskScoreController {

    @Autowired
    private CompanyRiskScoreService companyRiskScoreService;

    @PostMapping
    public ResponseEntity<CompanyRiskScore>
    createCompanyRiskScore(@Valid @RequestBody CompanyRiskScore companyRiskScore) {
        log.info("Creating Company Risk Score: {}", companyRiskScore);
        return new ResponseEntity<>(companyRiskScoreService
                .createCompanyRiskScore(companyRiskScore), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CompanyRiskScore>> getAllCompanyRiskScore(){
        return new ResponseEntity<>(companyRiskScoreService.getAllCompanyRiskScore(),HttpStatus.OK);
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<CompanyRiskScore>
    getCompanyRiskScore(@PathVariable String companyName) {
        log.info("Getting company with : {} ", companyName);
        return new ResponseEntity<>(companyRiskScoreService
                .getCompanyRiskScore(companyName), HttpStatus.OK);
    }


    @PutMapping("/{companyName}")
    public ResponseEntity<CompanyRiskScore>
    updateCompanyRiskScore(@PathVariable String companyName,
                           @Valid @RequestBody CompanyRiskScore companyRiskScore) {
        log.info("Updating company with : {}", companyName);
        return new ResponseEntity<>(companyRiskScoreService
                .updateCompanyRiskScore(companyName, companyRiskScore), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{companyName}")
    public ResponseEntity<?> deleteCompanyRiskScore(@PathVariable String companyName) {
        log.info("Deleting Company with Company Name : {}", companyName);
        return new ResponseEntity<>(companyRiskScoreService.
                deleteCompanyRiskScore(companyName),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/dimensionkeys")
    public ResponseEntity<Set<String>> getAllDimensionKeys() {
        Set<String> allDimensionKeys = companyRiskScoreService.getAllDimensionKeys();
        if (allDimensionKeys.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allDimensionKeys);
    }
}





