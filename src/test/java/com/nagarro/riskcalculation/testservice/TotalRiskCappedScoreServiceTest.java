package com.nagarro.riskcalculation.testservice;

import com.nagarro.riskcalculation.model.CompanyRiskScore;
import com.nagarro.riskcalculation.model.RiskScoreLevel;
import com.nagarro.riskcalculation.model.ScoreCap;
import com.nagarro.riskcalculation.repository.CompanyRiskScoreRepository;
import com.nagarro.riskcalculation.repository.RiskScoreLevelRepository;
import com.nagarro.riskcalculation.repository.ScoreCapRepository;
import com.nagarro.riskcalculation.service.TotalRiskCappedScoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class TotalRiskCappedScoreServiceTest {

    @Mock
    private CompanyRiskScoreRepository companyRiskScoreRepository;
    @Mock
    private RiskScoreLevelRepository riskScoreLevelRepository;
    @Mock
    private ScoreCapRepository scoreCapRepository;

    @InjectMocks
    private TotalRiskCappedScoreService totalRiskCappedScoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //    @Test
//    void testTotalRiskCappedScore() {
//        // Mock data
//        CompanyRiskScore companyRiskScore = new CompanyRiskScore();
//        companyRiskScore.setCompanyName("TestCompany");
//        Map<String, String> dimensions = new HashMap<>();
//        dimensions.put("Dimension1", "50");
//        dimensions.put("Dimension2", "75");
//        companyRiskScore.setDimensions(dimensions);
//
//        List<CompanyRiskScore> companyRiskScoreList = Collections.singletonList(companyRiskScore);
//        when(companyRiskScoreRepository.findAll()).thenReturn(companyRiskScoreList);
//
//        List<RiskScoreLevel> riskScoreLevelList = new ArrayList<>();
////        RiskScoreLevel level1 = new RiskScoreLevel("Level1", 0.0, 50.0);
////        RiskScoreLevel level2 = new RiskScoreLevel("Level2", 51.0, 100.0);
////        riskScoreLevelList.add(level1);
////        riskScoreLevelList.add(level2);
//        when(riskScoreLevelRepository.findAll()).thenReturn(riskScoreLevelList);
//
//        List<ScoreCap> scoreCapList = new ArrayList<>();
//        ScoreCap scoreCap1 = new ScoreCap(1,new RiskScoreLevel(),1,50.0);
//        ScoreCap scoreCap2= new ScoreCap(2,new RiskScoreLevel(),2,60.0);
//        scoreCapList.add(scoreCap1);
//        scoreCapList.add(scoreCap2);
//        when(scoreCapRepository.findAll()).thenReturn(scoreCapList);
//
//        // Execute the method
//        Double result = totalRiskCappedScoreService.totalRiskCappedScore("TestCompany");
//
//        // Verify the result
//        Assertions.assertEquals(50.0, result);
//        verify(companyRiskScoreRepository, times(1)).findById("TestCompany");
//        verify(companyRiskScoreRepository, times(1)).findAll();
//        verify(riskScoreLevelRepository, times(1)).findAll();
//        verify(scoreCapRepository, times(1)).findAll();
//    }
    @Test
    public void testTotalRiskCappedScore_ValidScoresAndCaps() {
        // Mocking repositories
        CompanyRiskScoreRepository companyRiskScoreRepository = mock(CompanyRiskScoreRepository.class);
        RiskScoreLevelRepository riskScoreLevelRepository = mock(RiskScoreLevelRepository.class);
        ScoreCapRepository scoreCapRepository = mock(ScoreCapRepository.class);

        // Prepare test data
        List<CompanyRiskScore> companyRiskScoreList = new ArrayList<>();
        List<RiskScoreLevel> riskScoreLevelList = new ArrayList<>();
        List<ScoreCap> scoreCapList = new ArrayList<>();

        // Add a company risk score with dimension values for testing (you can add more if needed)
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Dimension1", "30");
        dimensions.put("Dimension2", "40");

        // Create a CompanyRiskScore object
        CompanyRiskScore companyRiskScore = new CompanyRiskScore("TCS", dimensions);
        companyRiskScoreList.add(companyRiskScore);

        // Add risk score levels for testing
        riskScoreLevelList.add(new RiskScoreLevel(1, "Low", 10.0, 20.0));
        riskScoreLevelList.add(new RiskScoreLevel(2, "Medium", 30.0, 40.0));
        riskScoreLevelList.add(new RiskScoreLevel(3, "High", 50.0, 60.0));

        // Add score caps for testing
        scoreCapList.add(new ScoreCap(1, new RiskScoreLevel(), 1, 50.0));
        scoreCapList.add(new ScoreCap(2, new RiskScoreLevel(), 2, 60.0));
        scoreCapList.add(new ScoreCap(3, new RiskScoreLevel(), 3, 70.0));

        // Mock repository behaviors
        when(companyRiskScoreRepository.findAll()).thenReturn(companyRiskScoreList);
        when(riskScoreLevelRepository.findAll()).thenReturn(riskScoreLevelList);
        when(scoreCapRepository.findAll()).thenReturn(scoreCapList);
        when(companyRiskScoreRepository.findById(any())).thenReturn(Optional.of(companyRiskScore));

        // Create an instance of the class containing the method (adjust if needed)

        // Call the method under test
        Double value = totalRiskCappedScoreService.totalRiskCappedScore("TCS");
        // Assert the result
        // The expected result can be calculated manually based on the provided data
        assertEquals(Double.valueOf(9.5), value);

    }

    @Test
    public void testTotalRiskCappedScore_MissingRiskScoreLevels() {
        // Mock repositories
        CompanyRiskScoreRepository companyRiskScoreRepository = mock(CompanyRiskScoreRepository.class);
        RiskScoreLevelRepository riskScoreLevelRepository = mock(RiskScoreLevelRepository.class);
        ScoreCapRepository scoreCapRepository = mock(ScoreCapRepository.class);

        // Prepare test data
        List<CompanyRiskScore> companyRiskScoreList = new ArrayList<>();
        List<RiskScoreLevel> riskScoreLevelList = new ArrayList<>();
        List<ScoreCap> scoreCapList = new ArrayList<>();

        // Add a company risk score with dimension values for testing (you can add more if needed)
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Dimension1", "7.5");
        dimensions.put("Dimension2", "4.3");

        // Create a CompanyRiskScore object
        CompanyRiskScore companyRiskScore = new CompanyRiskScore("TestCompany", dimensions);
        companyRiskScoreList.add(companyRiskScore);

        // Add score caps for testing
        scoreCapList.add(new ScoreCap(1, new RiskScoreLevel(), 1, 50.0));
        scoreCapList.add(new ScoreCap(2, new RiskScoreLevel(), 2, 60.0));
        scoreCapList.add(new ScoreCap(3, new RiskScoreLevel(), 3, 70.0));

        // Mock repository behaviors
        when(companyRiskScoreRepository.findAll()).thenReturn(companyRiskScoreList);
        when(riskScoreLevelRepository.findAll()).thenReturn(riskScoreLevelList);
        when(scoreCapRepository.findAll()).thenReturn(scoreCapList);
        when(companyRiskScoreRepository.findById("TestCompany")).thenReturn(Optional.of(companyRiskScore));

        // Create an instance of the class containing the method (adjust if needed)
        Double value = totalRiskCappedScoreService.totalRiskCappedScore("TCS");
       assertThrows(RuntimeException.class, () -> totalRiskCappedScoreService.totalRiskCappedScore("TCS"));


    }

    @Test
    public void testTotalRiskCappedScore_MissingScoreCaps() {
        // Mock repositories
        CompanyRiskScoreRepository companyRiskScoreRepository = mock(CompanyRiskScoreRepository.class);
        RiskScoreLevelRepository riskScoreLevelRepository = mock(RiskScoreLevelRepository.class);
        ScoreCapRepository scoreCapRepository = mock(ScoreCapRepository.class);

        // Prepare test data
        List<CompanyRiskScore> companyRiskScoreList = new ArrayList<>();
        List<RiskScoreLevel> riskScoreLevelList = new ArrayList<>();
        List<ScoreCap> scoreCapList = new ArrayList<>();

        // Add a company risk score with dimension values for testing (you can add more if needed)
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Dimension1", "7.5");
        dimensions.put("Dimension2", "4.3");

        // Create a CompanyRiskScore object
        CompanyRiskScore companyRiskScore = new CompanyRiskScore("TestCompany", dimensions);
        companyRiskScoreList.add(companyRiskScore);

        // Add risk score levels for testing
        riskScoreLevelList.add(new RiskScoreLevel(1, "Low", 0.0, 5.0));
        riskScoreLevelList.add(new RiskScoreLevel(2, "Medium", 5.0, 7.0));
        riskScoreLevelList.add(new RiskScoreLevel(3, "High", 7.0, 10.0));

        // Mock repository behaviors
        when(companyRiskScoreRepository.findAll()).thenReturn(companyRiskScoreList);
        when(riskScoreLevelRepository.findAll()).thenReturn(riskScoreLevelList);
        when(scoreCapRepository.findAll()).thenReturn(scoreCapList);
        when(companyRiskScoreRepository.findById("TestCompany")).thenReturn(Optional.of(companyRiskScore));

        // Create an instance of the class containing the method (adjust if needed)

        // Call the method under test
        Double value = totalRiskCappedScoreService.totalRiskCappedScore("TCS");

        // Assert the result, it should be the same as the uncapped total score
        assertEquals(Double.valueOf(11.8), value);
    }
}


//    @Test
//    void testFindLevelForValue() {
//        // Mock data
//        List<RiskScoreLevel> riskScoreLevelList = new ArrayList<>();
//        RiskScoreLevel level1 = new RiskScoreLevel(1,"low risk",20,30);
//        RiskScoreLevel level2 = new RiskScoreLevel(2,"high risk",40,50);
//
//        riskScoreLevelList.add(level1);
//        riskScoreLevelList.add(level2);
//
//
//
//        // Verify the results
//        assertEquals("Level1", result1);
//        assertEquals("Level2", result2);
//        assertEquals("Level in Range not found", result3);
//    }
//
//    @Test
//    void testEvaluateTotalRiskCappedScore() {
//        // Mock data
//        Map<String, Integer> levelCountMap = new HashMap<>();
//        levelCountMap.put("Level1", 2);
//        levelCountMap.put("Level2", 1);
//
//        List<ScoreCap> scoreCapList = new ArrayList<>();
//        ScoreCap scoreCap1 = new ScoreCap(1,new RiskScoreLevel(),1,50.0);
//        ScoreCap scoreCap2= new ScoreCap(2,new RiskScoreLevel(),2,60.0);
//        scoreCapList.add(scoreCap1);
//        scoreCapList.add(scoreCap2);
//
//        // Execute the method
//        Double result = totalRiskCappedScoreService.evaluateTotalRiskCappedScore(levelCountMap, scoreCapList);
//
//        // Verify the result
//        assertEquals(75.0, result);
//    }


