package com.nagarro.riskcalculation.controller;
import com.nagarro.riskcalculation.model.RiskDimension;
import com.nagarro.riskcalculation.service.RiskDimensionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/riskDimensions")
public class RiskDimensionController {
    @Autowired
    private RiskDimensionService riskDimensionService;

//    @PostMapping
//    public ResponseEntity<RiskDimension> createRiskDimension(@RequestBody RiskDimension riskDimension) {
//        log.info("Creating Risk Dimension: {}", riskDimension);
//        return new ResponseEntity<>(riskDimensionService.createRiskDimension(riskDimension), HttpStatus.CREATED);
//    }
//
    @GetMapping("/{dimension}")
    public ResponseEntity<RiskDimension> getRiskDimension(@PathVariable String dimension) {
        log.info("Getting Risk Dimension: {}", dimension);
        return new ResponseEntity<>(riskDimensionService.getRiskDimension(dimension), HttpStatus.OK);
    }
//
    @GetMapping
    public ResponseEntity<List<RiskDimension>> getAllRiskDimensions() {
        log.info("Retrieved all Risk Dimensions: ");
        return new ResponseEntity<>(riskDimensionService.getAllRiskDimensions(), HttpStatus.OK);

    }
//
//    @PutMapping("/{dimension}")
//    public ResponseEntity<RiskDimension> updateRiskDimension(
//            @PathVariable String dimension, @RequestBody RiskDimension riskDimension) {
//        log.info("Updated Risk Dimension: {}", riskDimension);
//        return new ResponseEntity<>(riskDimensionService.updateRiskDimension(dimension, riskDimension), HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/{dimension}")
//    public ResponseEntity<?> deleteRiskDimension(@PathVariable String dimension) {
//        log.info("Deleted Risk Dimension with dimension: {}", dimension);
//        return new ResponseEntity<>(riskDimensionService.deleteRiskDimension(dimension), HttpStatus.NO_CONTENT);
//    }

    @PutMapping("/addUpdate")
   public ResponseEntity<List<RiskDimension>> createOrUpdateRiskDimensionsLists(@RequestBody List<RiskDimension> riskDimension) {

    //    List<RiskDimension> updateDimensionWeight = riskDimensionService.addOrUpdateDimensionWeight(riskDimension);
        return new ResponseEntity<>(riskDimensionService.addOrUpdateDimensionWeight(riskDimension),HttpStatus.CREATED);
    }
    @GetMapping("/weights")
    public ResponseEntity<List<String>> getAllDimensions() {
        List<String> allDimensions = riskDimensionService.getAllDimensions();
        if (allDimensions .isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allDimensions);
    }
}
