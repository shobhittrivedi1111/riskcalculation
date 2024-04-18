package com.nagarro.riskcalculation.testcontroller;
import com.nagarro.riskcalculation.controller.CompanyRiskScoreController;
import com.nagarro.riskcalculation.model.CompanyRiskScore;
import com.nagarro.riskcalculation.service.CompanyRiskScoreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CompanyRiskScoreControllerTest {

    @Mock
    private CompanyRiskScoreService companyRiskScoreService;

    @InjectMocks
    private CompanyRiskScoreController companyRiskScoreController;

    @Test
    void testCreateCompanyRiskScore() {
        Map<String,String> riskMap=new HashMap<>();
        riskMap.put("Information Security","70");
        riskMap.put("Conduct","50");
        riskMap.put("Resilience","60");
        // Mock data
        CompanyRiskScore companyRiskScore = new CompanyRiskScore("TCS",riskMap);
        when(companyRiskScoreService.createCompanyRiskScore(companyRiskScore)).thenReturn(companyRiskScore);
        // Execute the method
        ResponseEntity<CompanyRiskScore> response = companyRiskScoreController.createCompanyRiskScore(companyRiskScore);
        // Verify the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(companyRiskScore, response.getBody());
        verify(companyRiskScoreService, times(1)).createCompanyRiskScore(companyRiskScore);
    }

    @Test
    void testGetAllCompanyRiskScore() {
        Map<String,String> riskMap1=new HashMap<>();
        riskMap1.put("Information Security","70");
        riskMap1.put("Conduct","50");
        riskMap1.put("Resilience","60");
        // Mock data
        CompanyRiskScore companyRiskScore1 = new CompanyRiskScore("TCS",riskMap1);
        Map<String,String> riskMap2=new HashMap<>();
        riskMap1.put("Information Security","30");
        riskMap1.put("Conduct","40");
        riskMap1.put("Resilience","20");
        // Mock data
        CompanyRiskScore companyRiskScore2 = new CompanyRiskScore("Infosys",riskMap2);
        // Mock data
        List<CompanyRiskScore> companyRiskScores = new ArrayList<>();
        companyRiskScores.add(companyRiskScore1);
        companyRiskScores.add(companyRiskScore2);
        when(companyRiskScoreService.getAllCompanyRiskScore()).thenReturn(companyRiskScores);

        // Execute the method
        ResponseEntity<List<CompanyRiskScore>> response = companyRiskScoreController.getAllCompanyRiskScore();

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(companyRiskScores, response.getBody());
        verify(companyRiskScoreService, times(1)).getAllCompanyRiskScore();
    }

    @Test
    void testGetCompanyRiskScore() {
        // Mock data
        String companyName = "TCS";
        Map<String,String> riskMap=new HashMap<>();
        riskMap.put("Information Security","70");
        riskMap.put("Conduct","50");
        riskMap.put("Resilience","60");
        // Mock data
        CompanyRiskScore companyRiskScore = new CompanyRiskScore("TCS",riskMap);
        when(companyRiskScoreService.getCompanyRiskScore(companyName)).thenReturn(companyRiskScore);
        // Execute the method
        ResponseEntity<CompanyRiskScore> response = companyRiskScoreController.getCompanyRiskScore(companyName);
        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(companyRiskScore, response.getBody());
        System.out.println(response);
        verify(companyRiskScoreService, times(1)).getCompanyRiskScore(companyName);
    }

    @Test
    void testUpdateCompanyRiskScore() {
        // Mock data
        String companyName = "TCS";
        Map<String,String> riskMap=new HashMap<>();
        riskMap.put("Information Security","70");
        riskMap.put("Conduct","50");
        riskMap.put("Resilience","60");
        CompanyRiskScore updatedCompanyRiskScore = new CompanyRiskScore(companyName,riskMap);
        when(companyRiskScoreService.updateCompanyRiskScore(companyName, updatedCompanyRiskScore)).thenReturn(updatedCompanyRiskScore);
        // Execute the method
        ResponseEntity<CompanyRiskScore> response = companyRiskScoreController.updateCompanyRiskScore(companyName, updatedCompanyRiskScore);
        // Verify the result
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(updatedCompanyRiskScore, response.getBody());
        verify(companyRiskScoreService, times(1)).updateCompanyRiskScore(companyName, updatedCompanyRiskScore);
    }

    @Test
    void testDeleteCompanyRiskScore() {
        // Mock data
        String companyName = "TCS";
        when(companyRiskScoreService.deleteCompanyRiskScore(companyName)).thenReturn(true);
        // Execute the method
        ResponseEntity<?> response = companyRiskScoreController.deleteCompanyRiskScore(companyName);
        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(companyRiskScoreService, times(1)).deleteCompanyRiskScore(companyName);
    }
}
