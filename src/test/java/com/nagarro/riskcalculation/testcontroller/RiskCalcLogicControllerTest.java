package com.nagarro.riskcalculation.testcontroller;
import com.nagarro.riskcalculation.controller.RiskCalcLogicController;
import com.nagarro.riskcalculation.model.RiskCalcLogic;
import com.nagarro.riskcalculation.service.RiskCalcLogicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
class RiskCalcLogicControllerTest {

    @Mock
    private RiskCalcLogicService riskCalcLogicService;

    @InjectMocks
    private RiskCalcLogicController riskCalcLogicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRiskCalcLogics() {
        // Mock data
        List<RiskCalcLogic> riskCalcLogics = new ArrayList<>();
        riskCalcLogics.add(new RiskCalcLogic(1, "info_sec_weight", "Information Security*Information Security's weight"));
        riskCalcLogics.add(new RiskCalcLogic(2, "resilience_weight", "Resilience*Resilience's weight"));
        when(riskCalcLogicService.getAllRiskCalcLogics()).thenReturn(riskCalcLogics);

        // Execute the method
        ResponseEntity<List<RiskCalcLogic>> response = riskCalcLogicController.getAllRiskCalcLogics();

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(riskCalcLogics, response.getBody());
        verify(riskCalcLogicService, times(1)).getAllRiskCalcLogics();
    }

    @Test
    void testGetRiskCalcLogicById() {
        // Mock data
        int id = 1;
        RiskCalcLogic riskCalcLogic = new RiskCalcLogic( 1, "info_sec_weight", "Information Security*Information Security's weight");
        when(riskCalcLogicService.getRiskCalcLogicById(id)).thenReturn(riskCalcLogic);
        // Execute the method
        ResponseEntity<RiskCalcLogic> response = riskCalcLogicController.getRiskCalcLogicById(id);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(riskCalcLogic, response.getBody());
        verify(riskCalcLogicService, times(1)).getRiskCalcLogicById(id);
    }

    @Test
    void testCreateRiskCalcLogic() {
        // Mock data
        RiskCalcLogic riskCalcLogic = new RiskCalcLogic( 1, "info_sec_weight", "Information Security*Information Security's weight");
        when(riskCalcLogicService.createRiskCalcLogic(riskCalcLogic)).thenReturn(riskCalcLogic);
        // Execute the method
        ResponseEntity<RiskCalcLogic> response = riskCalcLogicController.createRiskCalcLogic(riskCalcLogic);
        // Verify the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(riskCalcLogic, response.getBody());
        verify(riskCalcLogicService, times(1)).createRiskCalcLogic(riskCalcLogic);
    }

    @Test
    void testUpdateRiskCalcLogic() {
        // Mock data
        int id = 1;
        RiskCalcLogic updatedRiskCalcLogic = new RiskCalcLogic( 1, "info_sec_weight", "Information Security*Information Security's weight");
        when(riskCalcLogicService.updateRiskCalcLogic(id, updatedRiskCalcLogic)).thenReturn(updatedRiskCalcLogic);

        // Execute the method
        ResponseEntity<RiskCalcLogic> response = riskCalcLogicController.updateRiskCalcLogic(id, updatedRiskCalcLogic);

        // Verify the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedRiskCalcLogic, response.getBody());
        verify(riskCalcLogicService, times(1)).updateRiskCalcLogic(id, updatedRiskCalcLogic);
    }

    @Test
    void testDeleteRiskCalcLogic() {
        // Mock data
        int id = 1;
        when(riskCalcLogicService.deleteRiskCalcLogic(id)).thenReturn(true);

        // Execute the method
        ResponseEntity<?> response = riskCalcLogicController.deleteRiskCalcLogic(id);

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(riskCalcLogicService, times(1)).deleteRiskCalcLogic(id);
    }
}
