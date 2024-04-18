package com.nagarro.riskcalculation.testcontroller;

import com.nagarro.riskcalculation.controller.RiskDimensionController;
import com.nagarro.riskcalculation.model.RiskDimension;
import com.nagarro.riskcalculation.service.RiskDimensionService;
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
class RiskDimensionControllerTest {

//    @Mock
//    private RiskDimensionService riskDimensionService;
//
//    @InjectMocks
//    private RiskDimensionController riskDimensionController;
//
//    @Test
//    void testCreateRiskDimension() {
//        // Mock data
//        RiskDimension riskDimension = new RiskDimension("Conduct's weight","13%");
//        when(riskDimensionService.createRiskDimension(riskDimension)).thenReturn(riskDimension);
//        ResponseEntity<RiskDimension> response = riskDimensionController.createRiskDimension(riskDimension);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(riskDimension, response.getBody());
//        verify(riskDimensionService, times(1)).createRiskDimension(riskDimension);
//    }
//
//    @Test
//    void testGetRiskDimension() {
//        // Mock data
//        String dimension = "Conduct's weight";
//        RiskDimension riskDimension = new RiskDimension("Conduct's weight","13%");
//        when(riskDimensionService.getRiskDimension(dimension)).thenReturn(riskDimension);
//        ResponseEntity<RiskDimension> response = riskDimensionController.getRiskDimension(dimension);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(riskDimension, response.getBody());
//        verify(riskDimensionService, times(1)).getRiskDimension(dimension);
//    }
//
//    @Test
//    void testGetAllRiskDimensions() {
//        // Mock data
//        List<RiskDimension> riskDimensions = new ArrayList<>();
//        riskDimensions.add(new RiskDimension("Information Security","47%"));
//        riskDimensions.add(new RiskDimension("Conduct's Weight","13%"));
//        when(riskDimensionService.getAllRiskDimensions()).thenReturn(riskDimensions);
//
//        // Execute the method
//        ResponseEntity<List<RiskDimension>> response = riskDimensionController.getAllRiskDimensions();
//
//        // Verify the result
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(riskDimensions, response.getBody());
//        verify(riskDimensionService, times(1)).getAllRiskDimensions();
//    }
//
//    @Test
//    void testUpdateRiskDimension() {
//        String dimension = "Information Security";
//        RiskDimension riskDimension = new RiskDimension("Information Security","40%");
//        // Mock data
//        RiskDimension updatedDimension = new RiskDimension("Information Security","35%");
//        when(riskDimensionService.updateRiskDimension(dimension, riskDimension)).thenReturn(riskDimension);
//        // Execute the method
//        ResponseEntity<RiskDimension> response = riskDimensionController.updateRiskDimension(dimension, riskDimension);
//        // Verify the result
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(riskDimension, response.getBody());
//        verify(riskDimensionService, times(1)).updateRiskDimension(dimension, riskDimension);
//    }
//
//    @Test
//    void testDeleteRiskDimension() {
//        // Mock data
//        String dimension = "Information Security";
//        when(riskDimensionService.deleteRiskDimension(dimension)).thenReturn(true);
//        // Execute the method
//        ResponseEntity<?> response = riskDimensionController.deleteRiskDimension(dimension);
//        // Verify the result
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(riskDimensionService, times(1)).deleteRiskDimension(dimension);
//    }
}

