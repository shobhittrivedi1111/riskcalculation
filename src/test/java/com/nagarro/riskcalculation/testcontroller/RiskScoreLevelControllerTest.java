package com.nagarro.riskcalculation.testcontroller;

import com.nagarro.riskcalculation.controller.RiskScoreLevelController;
import com.nagarro.riskcalculation.model.RiskScoreLevel;
import com.nagarro.riskcalculation.service.RiskScoreLevelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class RiskScoreLevelControllerTest {

    @Mock
    private RiskScoreLevelService riskScoreLevelService;

    @InjectMocks
    private RiskScoreLevelController riskScoreLevelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRiskScoreLevels() {
        // Mock data
        List<RiskScoreLevel> riskScoreLevels = new ArrayList<>();
        riskScoreLevels.add(new RiskScoreLevel(1, "Very low risk", 81.0, 100.0));
        riskScoreLevels.add(new RiskScoreLevel(2, "Low Risk ", 61.0, 80.0));
        when(riskScoreLevelService.getAllRiskScoreLevels()).thenReturn(riskScoreLevels);

        // Execute the method
        ResponseEntity<List<RiskScoreLevel>> response = riskScoreLevelController.getAllRiskScoreLevels();

        // Verify the result
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(riskScoreLevels, response.getBody());
        verify(riskScoreLevelService, times(1)).getAllRiskScoreLevels();
    }

    @Test
    void testGetRiskScoreLevelById() {
        // Mock data
        RiskScoreLevel riskScoreLevel = new RiskScoreLevel(1, "Very low risk", 81.0, 100.0);
        when(riskScoreLevelService.getRiskScoreLevelById(1)).thenReturn(riskScoreLevel);

        // Execute the method
        ResponseEntity<RiskScoreLevel> response = riskScoreLevelController.getRiskScoreLevelById(1);

        // Verify the result
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(riskScoreLevel, response.getBody());
        verify(riskScoreLevelService, times(1)).getRiskScoreLevelById(1);
    }

    @Test
    void testCreateRiskScoreLevel() {
        // Mock data
        RiskScoreLevel riskScoreLevel = new RiskScoreLevel(1, "Very low risk", 81.0, 100.0);
        when(riskScoreLevelService.createRiskScoreLevel(riskScoreLevel)).thenReturn(riskScoreLevel);

        // Execute the method
        ResponseEntity<RiskScoreLevel> response = riskScoreLevelController.createRiskScoreLevel(riskScoreLevel);

        // Verify the result
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(riskScoreLevel, response.getBody());
        verify(riskScoreLevelService, times(1)).createRiskScoreLevel(riskScoreLevel);
    }

    @Test
    void testUpdateRiskScoreLevel() {
        // Mock data
        RiskScoreLevel riskScoreLevel = new RiskScoreLevel(1, "Very low risk", 81.0, 100.0);
        when(riskScoreLevelService.updateRiskScoreLevel(1, riskScoreLevel)).thenReturn(riskScoreLevel);

        // Execute the method
        ResponseEntity<RiskScoreLevel> response = riskScoreLevelController.updateRiskScoreLevel(1, riskScoreLevel);

        // Verify the result
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(riskScoreLevel, response.getBody());
        verify(riskScoreLevelService, times(1)).updateRiskScoreLevel(1, riskScoreLevel);
    }

    @Test
    void testDeleteRiskScoreLevel() {
        // Execute the method
        ResponseEntity<?> response = riskScoreLevelController.deleteRiskScoreLevel(1);
        // Verify the result
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(riskScoreLevelService, times(1)).deleteRiskScoreLevel(1);
    }
}
