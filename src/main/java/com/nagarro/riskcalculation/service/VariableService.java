package com.nagarro.riskcalculation.service;

import com.nagarro.riskcalculation.model.CompanyRiskScore;
import com.nagarro.riskcalculation.model.RiskCalcLogic;
import com.nagarro.riskcalculation.model.RiskDimension;
import com.nagarro.riskcalculation.repository.CompanyRiskScoreRepository;
import com.nagarro.riskcalculation.repository.RiskCalcLogicRepository;
import com.nagarro.riskcalculation.repository.RiskDimensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VariableService {

        @Autowired
        private  RiskCalcLogicRepository riskCalcLogicRepository;
        @Autowired
        private RiskDimensionRepository riskDimensionRepository;
        @Autowired
        private CompanyRiskScoreRepository companyRiskScoreRepository;



        public Set<String> getAllVariable() {
            Set<String> allVariables= new HashSet<>();
            Set<String> elementNames = getAllElementNames();
            Set<String> allDimensions = getAllDimensions();
            Set<String> allDimensionKeys = getAllDimensionKeys();
            allVariables.addAll(elementNames);
            allVariables.addAll(allDimensions);
            allVariables.addAll(allDimensionKeys);
            return allVariables;
        }

        public Set<String> getAllElementNames() {
            List<RiskCalcLogic> riskCalcLogics = riskCalcLogicRepository.findAll();
            Set<String> elementNames = new HashSet<>();
            for (RiskCalcLogic riskCalcLogic : riskCalcLogics) {
                elementNames.add(riskCalcLogic.getElementName());
            }
            return elementNames;
        }

        public Set<String> getAllDimensions() {
            List<RiskDimension> allRiskDimensions = riskDimensionRepository.findAll();
            Set<String> allDimensions = new HashSet<>();

            for (RiskDimension riskDimension : allRiskDimensions) {
                allDimensions.add(riskDimension.getDimension());
            }

            return allDimensions;
        }

        public Set<String> getAllDimensionKeys() {
            List<CompanyRiskScore> allCompanyRiskScores = companyRiskScoreRepository.findAll();
            Set<String> allDimensionKeys = new HashSet<>();

            for (CompanyRiskScore companyRiskScore : allCompanyRiskScores) {
                allDimensionKeys.addAll(companyRiskScore.getDimensions().keySet());
            }

            return allDimensionKeys;
        }
    }

