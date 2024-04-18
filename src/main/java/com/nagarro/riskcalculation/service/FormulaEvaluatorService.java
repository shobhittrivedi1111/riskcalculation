package com.nagarro.riskcalculation.service;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import com.nagarro.riskcalculation.advice.FormulaEvaluationException;
import com.nagarro.riskcalculation.model.*;
import com.nagarro.riskcalculation.repository.CompanyRiskScoreRepository;
import com.nagarro.riskcalculation.repository.OutputRepository;
import com.nagarro.riskcalculation.repository.RiskCalcLogicRepository;
import com.nagarro.riskcalculation.repository.RiskDimensionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
/*
public class FormulaEvaluatorService {
    @Autowired
    private CompanyRiskScoreRepository companyRiskScoreRepository;
    @Autowired
    private RiskCalcLogicRepository riskCalcLogicRepository;
    @Autowired
    private RiskDimensionRepository riskDimensionRepository;
    @Autowired
    private TotalRiskCappedScoreService totalRiskCappedScoreService;
    @Autowired
    private OutputRepository outputRepository;
    @Autowired
    private  JobExecutionService jobExecutionService;

    Set<String> failedCompanies=new HashSet<>();
    public void evaluateFormulas(String jobName) {
        try {
            failedCompanies.clear();
            log.info("Starting formula evaluation.");
            List<CompanyRiskScore> companyRiskScoreList = companyRiskScoreRepository.findAll();
            List<RiskCalcLogic> riskCalcLogicList = riskCalcLogicRepository.findAll();
            List<RiskDimension> riskDimensionList = riskDimensionRepository.findAll();
            for (CompanyRiskScore riskScore : companyRiskScoreList) {
                // Create a map to store the results of each formula
                Map<String, Double> formulaResults = new HashMap<>();
                // Create a map of dimension names and their weights for easy lookup
                Map<String, Double> dimensionWeights = new HashMap<>();
                for (RiskDimension dimension : riskDimensionList) {
                    dimensionWeights.put(dimension.getDimension(), Double.valueOf(dimension.getWeight()));
                }

                // Step 1: Set up Javaluator evaluator
                DoubleEvaluator evaluator = new DoubleEvaluator();
                StaticVariableSet<Double> variables = new StaticVariableSet<>();
                variables.set("total_risk_capped_score", totalRiskCappedScoreService.totalRiskCappedScore(riskScore.getCompanyName()));
                // Set variables for dimension weights
                for (Map.Entry<String, Double> entry : dimensionWeights.entrySet()) {
                    variables.set(entry.getKey(), entry.getValue());
                }
                // Step 2: Evaluate each formula and store the results
                for (RiskCalcLogic calcLogic : riskCalcLogicList) {
//                    String elementName= calcLogic.getElement_name();
                    String formula = calcLogic.getFormula();
                    // Replace dimension values in the formula with their actual values from the risk score
                    for (Map.Entry<String, String> entry : riskScore.getDimensions().entrySet()) {
                        variables.set(entry.getKey(), Double.valueOf(entry.getValue()));
                    }
                    for (Map.Entry<String, Double> entry : formulaResults.entrySet()) {
                        variables.set(entry.getKey(), entry.getValue());
                    }

                    // Step 3: Evaluate the formula
                    try {
                        Double result = evaluator.evaluate(formula, variables);
                        log.info("Company: {}", riskScore.getCompanyName());
                        log.info("Formula: {}", formula);
                        log.info("Result: {}", result);
                        // Store the result in the formulaResults map
                        formulaResults.put(calcLogic.getElement_name(), Double.valueOf(result));
                    } catch (Exception e) {
                        log.error("Failed to evaluate the formula: {}", e.getMessage());
                        failedCompanies.add(riskScore.getCompanyName());
                    }
                }
                // Create an Output object and save it to the output table
                Output output = new Output();
                output.setCompanyName(riskScore.getCompanyName());
                output.setFormulaOutput(convertToStringMap(formulaResults));
                outputRepository.save(output);
            }
            JobExecution jobExecution=new JobExecution();
            if (failedCompanies.isEmpty()) {
                jobExecution.setStatus(JobStatus.SUCCESS);

                jobExecutionService.saveJobExecution("None", jobExecution.getStatus(),jobName);
            } else {
                jobExecution.setStatus(JobStatus.FAILURE);
                jobExecutionService.saveJobExecution(failedCompanies.toString(), jobExecution.getStatus(),jobName);
            }
        } catch (Exception e) {
            log.error("Error during formula evaluation: {}", e.getMessage());
            throw new FormulaEvaluationException("Failed to evaluate formulas");
        }
    }
//     Helper method to convert the formula results map to a map of strings

    private Map<String, String> convertToStringMap(Map<String, Double> formulaResults) {
        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : formulaResults.entrySet()) {
            stringMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return stringMap;
    }
}
*/

public class FormulaEvaluatorService {
    @Autowired
    private CompanyRiskScoreRepository companyRiskScoreRepository;
    @Autowired
    private RiskCalcLogicRepository riskCalcLogicRepository;
    @Autowired
    private RiskDimensionRepository riskDimensionRepository;
    @Autowired
   private TotalRiskCappedScoreService totalRiskCappedScoreService;
    @Autowired
    private OutputRepository outputRepository;
    @Autowired
    private JobExecutionService jobExecutionService;

    Set<String> failedCompanies = new HashSet<>();

    public void evaluateFormulas(String jobName) {
        try {
            failedCompanies.clear();
            log.info("Starting formula evaluation.");

            List<CompanyRiskScore> companyRiskScoreList = companyRiskScoreRepository.findAll();
            List<RiskCalcLogic> riskCalcLogicList = riskCalcLogicRepository.findAll();
            List<RiskDimension> riskDimensionList = riskDimensionRepository.findAll();

            // Step 1: Sort the formulas based on dependencies to perform a topological sort
            List<RiskCalcLogic> sortedRiskCalcLogicList = sortFormulasByDependencies(riskCalcLogicList);

            for (CompanyRiskScore riskScore : companyRiskScoreList) {
                // Create a map to store the results of each formula
                Map<String, Double> formulaResults = new HashMap<>();

                // Create a map of dimension names and their weights for easy lookup
                Map<String, Double> dimensionWeights = new HashMap<>();
                for (RiskDimension dimension : riskDimensionList) {
                    dimensionWeights.put(dimension.getDimension(), Double.valueOf(dimension.getWeight()));
                }

                // Step 2: Set up Javaluator evaluator
                DoubleEvaluator evaluator = new DoubleEvaluator();
                StaticVariableSet<Double> variables = new StaticVariableSet<>();
              variables.set("total_risk_capped_score", totalRiskCappedScoreService.totalRiskCappedScore(riskScore.getCompanyName()));

                // Set variables for dimension weights
                for (Map.Entry<String, Double> entry : dimensionWeights.entrySet()) {
                    variables.set(entry.getKey(), entry.getValue());
                }

                // Step 3: Evaluate each formula and store the results
                for (RiskCalcLogic calcLogic : sortedRiskCalcLogicList) {
                    String formula = calcLogic.getFormula();

                    // Replace dimension values in the formula with their actual values from the risk score
                    for (Map.Entry<String, String> entry : riskScore.getDimensions().entrySet()) {
                        variables.set(entry.getKey(), Double.valueOf(entry.getValue()));
                    }
                    for (Map.Entry<String, Double> entry : formulaResults.entrySet()) {
                        variables.set(entry.getKey(), entry.getValue());
                    }

                    // Step 4: Evaluate the formula
                    try {
                        Double result = evaluator.evaluate(formula, variables);
                        log.info("Company: {}", riskScore.getCompanyName());
                        log.info("Formula: {}", formula);
                     /*   log.info("Result: {}", result);
                        // Store the result in the formulaResults map
                        formulaResults.put(calcLogic.getElement_name(), Double.valueOf(result));*/
                        // Format the result with two decimal places
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String formattedResult = decimalFormat.format(result);
                        log.info("Result: {}", formattedResult);
                        // Store the formatted result in the formulaResults map
                        formulaResults.put(calcLogic.getElementName(), Double.valueOf(formattedResult));
                    } catch (Exception e) {
                        log.error("Failed to evaluate the formula: {}", e.getMessage());
                        failedCompanies.add(riskScore.getCompanyName());
                    }
                }

               formulaResults.put("total_risk_capped_score", totalRiskCappedScoreService.totalRiskCappedScore(riskScore.getCompanyName()));

                // Step 5: Create an Output object and save it to the output table
                Output output = new Output();
                output.setCompanyName(riskScore.getCompanyName());
                output.setFormulaOutput(convertToStringMap(formulaResults));
                outputRepository.save(output);
            }
            log.info(sortedRiskCalcLogicList.toString());
            // Save job execution details based on the success/failure of formula evaluation
            JobExecution jobExecution = new JobExecution();
            if (failedCompanies.isEmpty()) {
                jobExecution.setStatus(JobStatus.SUCCESS);
                jobExecutionService.saveJobExecution("None", jobExecution.getStatus(), jobName);
            } else {
                jobExecution.setStatus(JobStatus.FAILURE);
                jobExecutionService.saveJobExecution(failedCompanies.toString(), jobExecution.getStatus(), jobName);
            }
        } catch (Exception e) {
            log.error("Error during formula evaluation: {}", e.getMessage());
            throw new FormulaEvaluationException("Failed to evaluate formulas");
        }
    }
    // Helper method to perform topological sort on formulas based on dependencies
    private List<RiskCalcLogic> sortFormulasByDependencies(List<RiskCalcLogic> riskCalcLogicList) {
        // Create a graph to represent the dependencies
        Map<String, List<String>> dependencies = new HashMap<>();
        for (RiskCalcLogic calcLogic : riskCalcLogicList) {
            dependencies.put(calcLogic.getElementName(), getDependentElements(calcLogic.getFormula()));
        }

        // Perform topological sort
        List<String> sortedElements = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String element : dependencies.keySet()) {
            if (!visited.contains(element)) {
                topologicalSort(element, dependencies, visited, sortedElements);
            }
        }

        // Create the sorted list of formulas based on the sorted elements
        List<RiskCalcLogic> sortedRiskCalcLogicList = new ArrayList<>();
        for (String element : sortedElements) {
            for (RiskCalcLogic calcLogic : riskCalcLogicList) {
                if (calcLogic.getElementName().equals(element)) {
                    sortedRiskCalcLogicList.add(calcLogic);
                    break;
                }
            }
        }

        return sortedRiskCalcLogicList;
    }
    // Helper method to perform topological sort on formulas
    private void topologicalSort(String element, Map<String, List<String>> dependencies, Set<String> visited, List<String> sortedElements) {
        visited.add(element);
        List<String> dependentElements = dependencies.get(element);
        if (dependentElements != null) {
            for (String dependentElement : dependentElements) {
                if (!visited.contains(dependentElement)) {
                    topologicalSort(dependentElement, dependencies, visited, sortedElements);
                }
            }
        }
        sortedElements.add(element);
    }
    // Helper method to extract dependent elements from a formula
    private List<String> getDependentElements(String formula) {
        List<String> dependentElements = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=\\W|^)([A-Za-z_]+)(?=\\W|$)");
        Matcher matcher = pattern.matcher(formula);
        while (matcher.find()) {
            dependentElements.add(matcher.group());
        }
        return dependentElements;
    }

    // Helper method to convert the formula results map to a map of strings
    private Map<String, String> convertToStringMap(Map<String, Double> formulaResults) {
        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : formulaResults.entrySet()) {
            stringMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return stringMap;
    }
}












