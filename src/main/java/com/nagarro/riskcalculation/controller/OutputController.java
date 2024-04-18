package com.nagarro.riskcalculation.controller;

import com.nagarro.riskcalculation.model.Output;
import com.nagarro.riskcalculation.service.OutputService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/output")
@Slf4j
public class OutputController {

    @Autowired
    private OutputService outputService;

    @PostMapping
    public ResponseEntity<Output> saveOutput(@RequestBody Output output) {

        log.info("Output saved successfully.");
        return new ResponseEntity<>(outputService.saveOutput(output), HttpStatus.CREATED);
    }
    @DeleteMapping("/{companyName}")
    public ResponseEntity<String> deleteOutputByCompany(@PathVariable String companyName) {
        log.info("Deleting output by CompanyName: {}", companyName);
        outputService.deleteOutputByCompany(companyName);
        return ResponseEntity.ok("Output deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Output>> getAllOutput(){
        log.info("Getting ouput for all company");
        return new ResponseEntity<>(outputService.getAllOutput(),HttpStatus.OK);
    }

}
