package com.nagarro.riskcalculation.testservice;
import com.nagarro.riskcalculation.advice.DataAlreadyExistsException;
import com.nagarro.riskcalculation.model.RiskScoreLevel;
import com.nagarro.riskcalculation.model.ScoreCap;
import com.nagarro.riskcalculation.repository.RiskScoreLevelRepository;
import com.nagarro.riskcalculation.repository.ScoreCapRepository;
import com.nagarro.riskcalculation.service.ScoreCapService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ScoreCapServiceTest {


        @InjectMocks
        private ScoreCapService scoreCapService;

        @Mock
        private ScoreCapRepository scoreCapRepository;

        @Mock
        private RiskScoreLevelRepository riskScoreLevelRepository;
@Test
 public void testGetAllScoreCaps_Success() {
  // Mock data
  List<ScoreCap> mockScoreCaps = new ArrayList<>();
  mockScoreCaps.add(new ScoreCap(1, new RiskScoreLevel(), 50, 70.0));
  mockScoreCaps.add(new ScoreCap(2, new RiskScoreLevel(), 30, 60.0));
  when(scoreCapRepository.findAll()).thenReturn(mockScoreCaps);
  // Call the service method
  List<ScoreCap> result = scoreCapService.getAllScoreCaps();
  // Assert the result
  assertNotNull(result);
  assertEquals(2, result.size());
  // You can further assert the contents of the result if required.
 }
    @Test
    public void testGetAllScoreCaps_EmptyList() {
        // Mocking the repository call to return an empty list
 when(scoreCapRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the service method
        List<ScoreCap> result = scoreCapService.getAllScoreCaps();

        // Assert the result
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetScoreCapById_ExistingId_Success() {
        // Mock data
        int existingId = 1;
        ScoreCap mockScoreCap = new ScoreCap(existingId, new RiskScoreLevel(), 50, 70.0);
        // Mocking the repository call to return the ScoreCap with the existing ID
      when(scoreCapRepository.findById(existingId)).thenReturn(Optional.of(mockScoreCap));
        // Call the service method
        ScoreCap result = scoreCapService.getScoreCapById(existingId);
        // Assert the result
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        // You can further assert other properties of the ScoreCap object if required.
    }

    @Test
    public void testGetScoreCapById_NonExistingId_Exception() {
        // Mock data
        int nonExistingId = 100;
        // Mocking the repository call to return an empty Optional, simulating non-existing ID
       when(scoreCapRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        // Call the service method and assert that it throws NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> scoreCapService.getScoreCapById(nonExistingId));
   }
//    @Test
//    public void testCreateScoreCap_Success() {
//        // Mock data
//        RiskScoreLevel riskScoreLevel = new RiskScoreLevel(1, "Low", 0, 50);
//        ScoreCap scoreCap = new ScoreCap(1, riskScoreLevel, 25, 30.0);
//
//        // Mocking the repository calls
//        when(scoreCapRepository.findById(scoreCap.getId())).thenReturn(Optional.empty());
//       when(scoreCapRepository.findByRiskScoreLevel_LevelIgnoreCaseAndConditionValue(scoreCap.getRiskScoreLevel().getLevel(),
//               scoreCap.getConditionValue())).thenReturn(Optional.empty());
//       when(riskScoreLevelRepository.findByLevel(scoreCap.getRiskScoreLevel().getLevel())).thenReturn(Optional.of(riskScoreLevel));
//       when(scoreCapRepository.save(any(ScoreCap.class))).thenReturn(scoreCap);
//
//        // Call the service method
//        ScoreCap result = scoreCapService.createScoreCap(scoreCap);
//
//        // Assert the result
//        assertNotNull(result);
//        assertEquals(scoreCap, result);
//    }

    @Test
    public void testCreateScoreCap_AlreadyExistsException_SameId() {
        // Mock data
        RiskScoreLevel riskScoreLevel = new RiskScoreLevel(1, "Low", 0, 50);
        ScoreCap existingScoreCap = new ScoreCap(1, riskScoreLevel, 25, 30.0);
        // Mocking the repository call to return an existing ScoreCap with the same ID
        when(scoreCapRepository.findById(existingScoreCap.getId())).thenReturn(Optional.of(existingScoreCap));
        // Call the service method and assert that it throws DataAlreadyExistsException
        assertThrows(DataAlreadyExistsException.class, () -> scoreCapService.createScoreCap(existingScoreCap));
    }

    @Test
    public void testCreateScoreCap_AlreadyExistsException_SameRiskScoreLevel() {
        // Mock data
        RiskScoreLevel riskScoreLevel = new RiskScoreLevel(1, "Low", 0, 50);
        ScoreCap existingScoreCap = new ScoreCap(2, riskScoreLevel, 25, 30.0);
        // Mocking the repository call to return an existing ScoreCap with the same RiskScoreLevel and ConditionValue
        when(scoreCapRepository.findByRiskScoreLevel_LevelIgnoreCaseAndConditionValue(existingScoreCap.getRiskScoreLevel().getLevel(), existingScoreCap.getConditionValue())).thenReturn((existingScoreCap));
        // Call the service method and assert that it throws DataAlreadyExistsException
        assertThrows(DataAlreadyExistsException.class, () -> scoreCapService.createScoreCap(existingScoreCap));
    }

    @Test
    public void testCreateScoreCap_RiskScoreLevelNotFoundException() {
        // Mock data
        RiskScoreLevel riskScoreLevel = new RiskScoreLevel(1, "Low", 0, 50);
        ScoreCap newScoreCap = new ScoreCap(3, riskScoreLevel, 25, 30.0);

        // Mocking the repository call to return an empty Optional, simulating RiskScoreLevel not found
        when(scoreCapRepository.findById(newScoreCap.getId())).thenReturn(Optional.empty());
        when(riskScoreLevelRepository.findByLevel(newScoreCap.getRiskScoreLevel().getLevel())).thenReturn(Optional.empty());

        // Call the service method and assert that it throws DataAlreadyExistsException
        assertThrows(DataAlreadyExistsException.class, () -> scoreCapService.createScoreCap(newScoreCap));
    }

    @Test
    public void testUpdateScoreCap_Success() {
        // Mock data
        int existingId = 1;
        RiskScoreLevel existingRiskScoreLevel = new RiskScoreLevel(1, "Low", 0, 50);
        ScoreCap existingScoreCap = new ScoreCap(existingId, existingRiskScoreLevel, 25, 30.0);

        int newConditionValue = 35;
        double newTotalRiskCappedScore = 40.0;
        RiskScoreLevel newRiskScoreLevel = new RiskScoreLevel(2, "Medium", 51, 75);
        ScoreCap updatedScoreCap = new ScoreCap(existingId, newRiskScoreLevel, newConditionValue, newTotalRiskCappedScore);

        // Mocking the repository calls
        Mockito.when(scoreCapRepository.findById(existingId)).thenReturn(Optional.of(existingScoreCap));
        Mockito.when(scoreCapRepository.findByRiskScoreLevel_LevelIgnoreCaseAndConditionValue(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(null); // Or return any existing ScoreCap object with the same riskScoreLevel and conditionValue
        Mockito.when(riskScoreLevelRepository.findByLevel(newRiskScoreLevel.getLevel())).thenReturn(Optional.of(newRiskScoreLevel));
        Mockito.when(scoreCapRepository.save(Mockito.any(ScoreCap.class))).thenReturn(updatedScoreCap);

        // Call the service method
        ScoreCap result = scoreCapService.updateScoreCap(existingId, updatedScoreCap);

        // Assert the result
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(newRiskScoreLevel, result.getRiskScoreLevel());
        assertEquals(newConditionValue, result.getConditionValue());
        assertEquals(newTotalRiskCappedScore, result.getTotal_risk_capped_score(), 0.001); // Provide a delta for double comparison
    }

    @Test
    public void testUpdateScoreCap_DataAlreadyExistsException() {
        // Mock data
        int existingId = 1;
        RiskScoreLevel existingRiskScoreLevel = new RiskScoreLevel(1, "Low", 0, 50);
        ScoreCap existingScoreCap = new ScoreCap(existingId, existingRiskScoreLevel, 25, 30.0);

        int newConditionValue = 35;
        double newTotalRiskCappedScore = 40.0;
        ScoreCap updatedScoreCap = new ScoreCap(2, existingRiskScoreLevel, newConditionValue, newTotalRiskCappedScore);

        // Mocking the repository calls
        when(scoreCapRepository.findById(existingId)).thenReturn(Optional.of(existingScoreCap));
        when(scoreCapRepository.findByRiskScoreLevel_LevelIgnoreCaseAndConditionValue(existingRiskScoreLevel.getLevel(), newConditionValue))
                .thenReturn(updatedScoreCap);

        // Call the service method and assert that it throws DataAlreadyExistsException
        assertThrows(DataAlreadyExistsException.class, () -> scoreCapService.updateScoreCap(existingId, updatedScoreCap));
    }

    @Test
    public void testUpdateScoreCap_NewRiskScoreLevel_Success() {
        // Mock data
        int existingId = 1;
        RiskScoreLevel existingRiskScoreLevel = new RiskScoreLevel(1, "Low", 0, 50);
        ScoreCap existingScoreCap = new ScoreCap(existingId, existingRiskScoreLevel, 25, 30.0);

        int newConditionValue = 35;
        double newTotalRiskCappedScore = 40.0;
        RiskScoreLevel newRiskScoreLevel = new RiskScoreLevel(2, "Medium", 51, 75);
        ScoreCap updatedScoreCap = new ScoreCap(existingId, newRiskScoreLevel, newConditionValue, newTotalRiskCappedScore);

        // Mocking the repository calls
        when(scoreCapRepository.findById(existingId)).thenReturn(Optional.of(existingScoreCap));
        when(scoreCapRepository.findByRiskScoreLevel_LevelIgnoreCaseAndConditionValue(newRiskScoreLevel.getLevel(), newConditionValue)).thenReturn(null);
        when(riskScoreLevelRepository.findByLevel(newRiskScoreLevel.getLevel())).thenReturn(Optional.empty());
        when(riskScoreLevelRepository.save(any(RiskScoreLevel.class))).thenReturn(newRiskScoreLevel);
        when(scoreCapRepository.save(any(ScoreCap.class))).thenReturn(updatedScoreCap); // Corrected

        // Call the service method
        ScoreCap result = scoreCapService.updateScoreCap(existingId, updatedScoreCap);

        // Assert the result
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(newRiskScoreLevel, result.getRiskScoreLevel());
        assertEquals(newConditionValue, result.getConditionValue());
        assertEquals(newTotalRiskCappedScore, result.getTotal_risk_capped_score(), 2.00);
    }





    @Test
    public void testDeleteScoreCap_Success() {
        // Mock data
        int existingId = 1;
        RiskScoreLevel existingRiskScoreLevel = new RiskScoreLevel(1, "Low", 0, 50);
        ScoreCap existingScoreCap = new ScoreCap(existingId, existingRiskScoreLevel, 25, 30.0);
        // Mocking the repository calls
        when(scoreCapRepository.findById(existingId)).thenReturn(Optional.of(existingScoreCap));
        // Call the service method
        boolean result = scoreCapService.deleteScoreCap(existingId);
        // Verify that the delete method was called on the repository
        verify(scoreCapRepository, times(1)).delete(existingScoreCap);
        // Assert the result
        assertTrue(result);
    }

    @Test
    public void testDeleteScoreCap_NoSuchElementException() {
        // Mock data
        int nonExistingId = 100;
        // Mocking the repository calls to return an empty Optional
       when(scoreCapRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        // Call the service method and assert that it throws NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> scoreCapService.deleteScoreCap(nonExistingId));
        // Verify that the delete method was not called on the repository
        verify(scoreCapRepository,times(0)).delete(any(ScoreCap.class));
    }
}











