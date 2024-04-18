package com.nagarro.riskcalculation.testservice;
import com.nagarro.riskcalculation.advice.DataAlreadyExistsException;
import com.nagarro.riskcalculation.model.CompanyRiskScore;
import com.nagarro.riskcalculation.repository.CompanyRiskScoreRepository;
import com.nagarro.riskcalculation.service.CompanyRiskScoreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CompanyRiskScoreServiceTest {
    @InjectMocks
    private CompanyRiskScoreService service;
    @Mock
    private CompanyRiskScoreRepository mockRepository;


    @Test
    public void testCreateCompanyRiskScore() {
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Information Security", "80");
        dimensions.put("Resilience", "60");
        dimensions.put("Conduct", "70");
        CompanyRiskScore companyRiskScore = new CompanyRiskScore("TCS", dimensions);
        // Set the properties of the CompanyRiskScore object
        when(mockRepository.findById(companyRiskScore.getCompanyName()))
                .thenReturn(Optional.empty());
        when(mockRepository.save(companyRiskScore)).thenReturn(companyRiskScore);
        CompanyRiskScore result = service.createCompanyRiskScore(companyRiskScore);
        assertEquals(companyRiskScore, result);
    }

    @Test
    public void testCreateCompanyRiskScore_AlreadyExists() {
           String companyName = "tcs";
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Information Security", "80");
        dimensions.put("Resilience", "60");
        dimensions.put("Conduct", "70");
        CompanyRiskScore companyRiskScore = new CompanyRiskScore("tcs", dimensions);
        // Set the properties of the CompanyRiskScore object
        // Set the properties of the CompanyRiskScore object
        when(mockRepository.findById(companyRiskScore.getCompanyName()))
                .thenReturn(Optional.of(companyRiskScore));
        assertThrows(DataAlreadyExistsException.class,
                () -> service.createCompanyRiskScore(companyRiskScore));
        verify(mockRepository, times(1)).findById(companyRiskScore.getCompanyName());

    }

    @Test
    public void testGetAllCompanyRiskScore() {
        List<CompanyRiskScore> expectedCompanyRiskScores = new ArrayList<>();
        Map<String, String> dimensions1 = new HashMap<>();
        dimensions1.put("Information Security", "80");
        dimensions1.put("Resilience", "60");
        dimensions1.put("Conduct", "70");
        CompanyRiskScore companyRiskScore1 = new CompanyRiskScore("TCS",dimensions1);
        expectedCompanyRiskScores.add(companyRiskScore1);
        System.out.println(companyRiskScore1);
        // Add some CompanyRiskScore objects to the list
        when(mockRepository.findAll()).thenReturn(expectedCompanyRiskScores);
        List<CompanyRiskScore> result = service.getAllCompanyRiskScore();
        assertEquals(expectedCompanyRiskScores, result);
    }

    @Test
    public void testGetCompanyRiskScore() {
        String companyName = "tcs";
         Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Information Security", "80");
        dimensions.put("Resilience", "60");
        dimensions.put("Conduct", "70");
        CompanyRiskScore expectedCompanyRiskScore=new CompanyRiskScore("tcs", dimensions);
        // Set the properties of the expected CompanyRiskScore object
        when(mockRepository.findById(companyName))
                .thenReturn(Optional.of(expectedCompanyRiskScore));
        CompanyRiskScore result = service.getCompanyRiskScore(companyName);
        assertEquals(expectedCompanyRiskScore,result);
    }



    @Test
    public void testGetCompanyRiskScore_NotFound() {
        String companyName = "TCS";
        when(mockRepository.findById(companyName)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> service.getCompanyRiskScore(companyName));
    }

    @Test
    public void testUpdateCompanyRiskScore() {
        String companyName = "tcs";
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Information Security", "80");
        dimensions.put("Resilience", "60");
        dimensions.put("Conduct", "70");
        CompanyRiskScore existingCompanyRiskScore= new CompanyRiskScore("tcs", dimensions);
        Map<String, String> dimensions1 = new HashMap<>();
        dimensions1.put("Information Security", "50");
        dimensions1.put("Resilience", "50");
        dimensions1.put("Conduct", "50");
        CompanyRiskScore updatedCompanyRiskScore = new CompanyRiskScore("tcs",dimensions1);
        // Set the properties of the updated CompanyRiskScore object
        when(mockRepository.findById(companyName))
                .thenReturn(Optional.of(existingCompanyRiskScore));
        when(mockRepository.save(existingCompanyRiskScore))
                .thenReturn(updatedCompanyRiskScore);
        CompanyRiskScore result = service.updateCompanyRiskScore(companyName, updatedCompanyRiskScore);
        assertEquals(updatedCompanyRiskScore, result);
    }

    @Test
    public void testUpdateCompanyRiskScore_NotFound() {
        String companyName = "tcs";
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Information Security", "80");
        dimensions.put("Resilience", "60");
        dimensions.put("Conduct", "70");
        CompanyRiskScore updatedCompanyRiskScore = new CompanyRiskScore("tcs",dimensions);
        // Set the properties of the updated CompanyRiskScore object
        when(mockRepository.findById(companyName)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> service.updateCompanyRiskScore(companyName, updatedCompanyRiskScore));
        verify(mockRepository,never()).save(any(CompanyRiskScore.class));
    }


    @Test
    public void testDeleteCompanyRiskScore() {
        String companyName = "tcs";
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("Information Security", "80");
        dimensions.put("Resilience", "60");
        dimensions.put("Conduct", "70");
        CompanyRiskScore existingCompanyRiskScore = new CompanyRiskScore(companyName, dimensions);
        when(mockRepository.findById(companyName))
                .thenReturn(Optional.of(existingCompanyRiskScore));
        boolean result = service.deleteCompanyRiskScore(companyName);
        assertTrue(result); // Assert that deletion was successful
        verify(mockRepository).delete(existingCompanyRiskScore);
    }

    @Test
    public void testDeleteCompanyRiskScore_NotFound() {
        String companyName = "tcs";
        when(mockRepository.findById(companyName)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> service.deleteCompanyRiskScore(companyName));
        verify(mockRepository, never()).delete(any(CompanyRiskScore.class));
    }

}


