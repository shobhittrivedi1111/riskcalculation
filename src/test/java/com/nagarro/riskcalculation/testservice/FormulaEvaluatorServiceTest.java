package com.nagarro.riskcalculation.testservice;
import com.nagarro.riskcalculation.model.*;
import com.nagarro.riskcalculation.repository.CompanyRiskScoreRepository;
import com.nagarro.riskcalculation.repository.OutputRepository;
import com.nagarro.riskcalculation.repository.RiskCalcLogicRepository;
import com.nagarro.riskcalculation.repository.RiskDimensionRepository;
import com.nagarro.riskcalculation.service.FormulaEvaluatorService;
import com.nagarro.riskcalculation.service.JobExecutionService;
import com.nagarro.riskcalculation.service.TotalRiskCappedScoreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class FormulaEvaluatorServiceTest {

    @Mock
    private CompanyRiskScoreRepository companyRiskScoreRepository;
    @Mock
    private RiskCalcLogicRepository riskCalcLogicRepository;
    @Mock
    private RiskDimensionRepository riskDimensionRepository;
    @Mock
    private TotalRiskCappedScoreService totalRiskCappedScoreService;
    @Mock
    private OutputRepository outputRepository;
    @Mock
    private JobExecutionService jobExecutionService;

    @InjectMocks
    private FormulaEvaluatorService formulaEvaluatorService;



    @Test
    public void testEvaluateFormulas_Success() {
        // Set up mock data
        List<CompanyRiskScore> mockCompanyRiskScores = createMockCompanyRiskScores();
        List<RiskCalcLogic> mockRiskCalcLogics = createMockRiskCalcLogics();
        List<RiskDimension> mockRiskDimensions = createMockRiskDimensions();

        when(companyRiskScoreRepository.findAll()).thenReturn(mockCompanyRiskScores);
        when(riskCalcLogicRepository.findAll()).thenReturn(mockRiskCalcLogics);
        when(riskDimensionRepository.findAll()).thenReturn(mockRiskDimensions);
        when(totalRiskCappedScoreService.totalRiskCappedScore(anyString())).thenReturn(50.0);
        when(outputRepository.save(any(Output.class))).thenReturn(new Output());
      //  when(jobExecutionService.saveJobExecution(anyString(), any(JobStatus.class), anyString())).thenReturn(new JobExecution());

        // Call the method under test
        formulaEvaluatorService.evaluateFormulas("JobA");

        // Verify interactions
        verify(companyRiskScoreRepository, times(1)).findAll();
        verify(riskCalcLogicRepository, times(1)).findAll();
        verify(riskDimensionRepository, times(1)).findAll();
        verify(totalRiskCappedScoreService, times(mockCompanyRiskScores.size())).totalRiskCappedScore(anyString());
        verify(outputRepository, times(mockCompanyRiskScores.size())).save(any(Output.class));
        verify(jobExecutionService, times(1)).saveJobExecution(eq("None"), eq(JobStatus.SUCCESS), eq("JobA"));
    }

    @Test
    public void testEvaluateFormulas_Failure() {
        // Set up mock data
        List<CompanyRiskScore> mockCompanyRiskScores = createMockCompanyRiskScores();
        List<RiskCalcLogic> mockRiskCalcLogics = createMockRiskCalcLogics();
        List<RiskDimension> mockRiskDimensions = createMockRiskDimensions();

        when(companyRiskScoreRepository.findAll()).thenReturn(mockCompanyRiskScores);
        when(riskCalcLogicRepository.findAll()).thenReturn(mockRiskCalcLogics);
        when(riskDimensionRepository.findAll()).thenReturn(mockRiskDimensions);
        when(totalRiskCappedScoreService.totalRiskCappedScore(anyString())).thenReturn(50.0);
        when(outputRepository.save(any(Output.class))).thenReturn(new Output());
     //   when(jobExecutionService.saveJobExecution(anyString(), any(JobStatus.class), anyString()))
//                .thenAnswer(invocation -> {
//                    return new JobExecution();
//                });

        // Set up a failure scenario
        when(companyRiskScoreRepository.findAll()).thenThrow(new RuntimeException("Error fetching risk scores"));

        // Call the method under test
        formulaEvaluatorService.evaluateFormulas("JobA");

        // Verify interactions
        verify(companyRiskScoreRepository, times(1)).findAll();
        verify(riskCalcLogicRepository, never()).findAll();
        verify(riskDimensionRepository, never()).findAll();
        verify(totalRiskCappedScoreService, never()).totalRiskCappedScore(anyString());
        verify(outputRepository, never()).save(any(Output.class));
        verify(jobExecutionService, times(1)).saveJobExecution(eq("[]"), eq(JobStatus.FAILURE), eq("JobA"));
    }

    private List<CompanyRiskScore> createMockCompanyRiskScores() {
        List<CompanyRiskScore> companyRiskScores = new ArrayList<>();

        Map<String, String> dimensions1 = new HashMap<>();
        dimensions1.put("Info Sec Weight", "40");
        dimensions1.put("Resilience Weight", "60");
        dimensions1.put("Conduct Weight", "70");
        companyRiskScores.add(new CompanyRiskScore("CompanyA", dimensions1));

        Map<String, String> dimensions2 = new HashMap<>();
        dimensions2.put("Info Sec Weight", "20");
        dimensions2.put("Resilience Weight", "30");
        dimensions2.put("Conduct Weight", "40");
        companyRiskScores.add(new CompanyRiskScore("CompanyB", dimensions2));

        return companyRiskScores;
    }

    private List<RiskCalcLogic> createMockRiskCalcLogics() {
        List<RiskCalcLogic> riskCalcLogics = new ArrayList<>();

        riskCalcLogics.add(new RiskCalcLogic(1,"conduct_weight", "Conduct Weight * 10"));
        riskCalcLogics.add(new RiskCalcLogic(2,"resilience_weight", "Resilience Weight * 5"));
        riskCalcLogics.add(new RiskCalcLogic(3,"total_risk_score", "conduct_weight + resilience_weight"));
        riskCalcLogics.add(new RiskCalcLogic(4,"composite_risk_score", "total_risk_score"));
        riskCalcLogics.add(new RiskCalcLogic(5,"info_sec_weight", "Info Sec Weight"));

        return riskCalcLogics;
    }

    private List<RiskDimension> createMockRiskDimensions() {
        List<RiskDimension> riskDimensions = new ArrayList<>();
//
//        riskDimensions.add(new RiskDimension("Info Sec Weight", "0.4"));
//        riskDimensions.add(new RiskDimension("Resilience Weight", "0.6"));
//        riskDimensions.add(new RiskDimension("Conduct Weight", "0.7"));

        return riskDimensions;
    }
}
