package com.nagarro.riskcalculation.testservice;

import com.nagarro.riskcalculation.model.CompanyRiskScore;
import com.nagarro.riskcalculation.model.RiskCalcLogic;
import com.nagarro.riskcalculation.model.RiskDimension;
import com.nagarro.riskcalculation.repository.CompanyRiskScoreRepository;
import com.nagarro.riskcalculation.repository.RiskCalcLogicRepository;
import com.nagarro.riskcalculation.repository.RiskDimensionRepository;
import com.nagarro.riskcalculation.service.VariableService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VariableServiceTest {
    @Mock
    private RiskCalcLogicRepository riskCalcLogicRepository;

    @Mock
    private RiskDimensionRepository riskDimensionRepository;

    @Mock
    private CompanyRiskScoreRepository companyRiskScoreRepository;

    @InjectMocks
    private VariableService variableService;
    @Test
    public void testGetAllVariable() {
        // Mocking the data returned by the repositories
        List<RiskCalcLogic> riskCalcLogics = Arrays.asList(
                new RiskCalcLogic(1,"element1","formula1"),
                new RiskCalcLogic(2,"element2","formula2"),
                new RiskCalcLogic(3,"element3","formula3")
        );
        when(riskCalcLogicRepository.findAll()).thenReturn(riskCalcLogics);

        List<RiskDimension> riskDimensions = Arrays.asList(
                new RiskDimension("dimensionWeight1",.50),
                new RiskDimension("dimensionWeight2",.50)
        );
        when(riskDimensionRepository.findAll()).thenReturn(riskDimensions);
        Map<String, String> dimension1 = new HashMap<>();
        dimension1.put("Information Security", "80");
        dimension1.put("Resilience", "60");
        dimension1.put("Conduct", "70");
        Map<String, String> dimension2 = new HashMap<>();
        dimension2.put("Information Security", "80");
        dimension2.put("Resilience", "60");
        dimension2.put("Conduct", "70");

        List<CompanyRiskScore> companyRiskScores = Arrays.asList(
                new CompanyRiskScore("tcs",dimension1),
                new CompanyRiskScore("infosys",dimension2)
        );
        when(companyRiskScoreRepository.findAll()).thenReturn(companyRiskScores);
        // Call the method under test
        Set<String> allVariables = variableService.getAllVariable();
        // Assert the result
        Set<String> expectedVariables = new HashSet<>(Arrays.asList("dimensionWeight2", "dimensionWeight1",
                "element1", "element2", "element3", "Information Security", "Conduct", "Resilience"));
        assertEquals(expectedVariables, allVariables);
    }

    @Test
    public void testGetAllElementNames() {
        // Mocking the data returned by the repository
        List<RiskCalcLogic> riskCalcLogics = Arrays.asList(
                new RiskCalcLogic(1,"element1","formula1"),
                new RiskCalcLogic(2,"element2","formula2"),
                new RiskCalcLogic(3,"element3","formula3")
        );
        when(riskCalcLogicRepository.findAll()).thenReturn(riskCalcLogics);

        // Call the method under test
        Set<String> allElementNames = variableService.getAllElementNames();

        // Assert the result
        Set<String> expectedElementNames = new HashSet<>(Arrays.asList("element1", "element2", "element3"));
        assertEquals(expectedElementNames, allElementNames);
    }

    @Test
    public void testGetAllDimensions() {
        // Mocking the data returned by the repository
        List<RiskDimension> riskDimensions = Arrays.asList(
                new RiskDimension("dimensionWeight1",.50),
                new RiskDimension("dimensionWeight2",.50)
        );
        when(riskDimensionRepository.findAll()).thenReturn(riskDimensions);

        // Call the method under test
        Set<String> allDimensions = variableService.getAllDimensions();

        // Assert the result
        Set<String> expectedDimensions = new HashSet<>(Arrays.asList("dimensionWeight1", "dimensionWeight2"));
        assertEquals(expectedDimensions, allDimensions);
    }

//    @Test
//    public void testGetAllDimensionKeys() {
//        // Mocking the data returned by the repository
//        Map<String, String> dimension1 = new HashMap<>();
//        dimension1.put("Information Security", "80");
//        dimension1.put("Resilience", "60");
//        dimension1.put("Conduct", "70");
//        Map<String, String> dimension2 = new HashMap<>();
//        dimension2.put("Information Security", "80");
//        dimension2.put("Resilience", "60");
//        dimension2.put("Conduct", "70");
//        List<CompanyRiskScore> companyRiskScores = Arrays.asList(
//                new CompanyRiskScore("tcs", (Map<String, String>) dimension1.keySet()),
//                new CompanyRiskScore("infosys", (Map<String, String>) dimension2.keySet())
//
//        );
//        when(companyRiskScoreRepository.findAll()).thenReturn(companyRiskScores);
//
//        // Call the method under test
//        Set<String> allDimensionKeys = variableService.getAllDimensionKeys();
//
//        // Assert the result
//        Set<String> expectedDimensionKeys = new HashSet<>(Arrays.asList("dimension1", "dimension2"));
//        assertEquals(expectedDimensionKeys, allDimensionKeys);
//    }
@Test
public void testGetAllDimensionKeys_EmptyList() {
    // Mocking an empty list returned by the repository
    when(companyRiskScoreRepository.findAll()).thenReturn(Collections.emptyList());

    // Call the method under test
    Set<String> allDimensionKeys = variableService.getAllDimensionKeys();

    // Assert the result
    assertTrue(allDimensionKeys.isEmpty());
}

    @Test
    public void testGetAllDimensionKeys_SingleCompanyRiskScore() {
        // Mocking a single CompanyRiskScore with one dimension key

        CompanyRiskScore companyRiskScore = new CompanyRiskScore();
        when(companyRiskScoreRepository.findAll()).thenReturn(Collections.singletonList(companyRiskScore));

        // Call the method under test
        Set<String> allDimensionKeys = variableService.getAllDimensionKeys();

        // Assert the result
        Set<String> expectedDimensionKeys = Collections.singleton("dimension1");
        assertEquals(expectedDimensionKeys, allDimensionKeys);
    }
//
//    @Test
//    public void testGetAllDimensionKeys_MultipleCompanyRiskScores() {
//        // Mocking multiple CompanyRiskScores with different dimension keys
//        CompanyRiskScore companyRiskScore1 = new CompanyRiskScore(Collections.singletonMap("dimension1", 10));
//        CompanyRiskScore companyRiskScore2 = new CompanyRiskScore(Collections.singletonMap("dimension2", 20));
//        CompanyRiskScore companyRiskScore3 = new CompanyRiskScore(Map.of("dimension1", 30, "dimension3", 40));
//        when(companyRiskScoreRepository.findAll()).thenReturn(Arrays.asList(companyRiskScore1, companyRiskScore2, companyRiskScore3));
//
//        // Call the method under test
//        Set<String> allDimensionKeys = variableService.getAllDimensionKeys();
//
//        // Assert the result
//        Set<String> expectedDimensionKeys = new HashSet<>(Arrays.asList("dimension1", "dimension2", "dimension3"));
//        assertEquals(expectedDimensionKeys, allDimensionKeys);
//    }
}



