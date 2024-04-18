package com.nagarro.riskcalculation.controller;
import com.nagarro.riskcalculation.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/variables")
public class VariableController {
        @Autowired
        private VariableService variableService;

        @GetMapping
        public ResponseEntity<Set<String>> getAllVariables() {
              return new ResponseEntity<>(variableService.getAllVariable(), HttpStatus.OK);
        }
    }
