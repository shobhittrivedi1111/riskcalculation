package com.nagarro.riskcalculation.testservice;
import com.nagarro.riskcalculation.model.RiskScoreLevel;
import com.nagarro.riskcalculation.repository.RiskScoreLevelRepository;
import com.nagarro.riskcalculation.service.RiskScoreLevelService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RiskScoreLevelServiceTest {
    @InjectMocks
    private RiskScoreLevelService service;

    @Mock
    private RiskScoreLevelRepository mockRepository;

    @Test
    public void testGetAllRiskScoreLevels() {
        List<RiskScoreLevel> expectedRiskScoreLevels = new ArrayList<>();
       expectedRiskScoreLevels.add(new RiskScoreLevel(1,"Very low risk",81.0,100.0));
       expectedRiskScoreLevels.add(new RiskScoreLevel(2,"Low risk",61.0,80.0));
        when(mockRepository.findAll()).thenReturn(expectedRiskScoreLevels);
        List<RiskScoreLevel> result = service.getAllRiskScoreLevels();
        assertEquals(expectedRiskScoreLevels, result);
    }

    @Test
    public void testGetRiskScoreLevelById() {
        int id = 1;
        RiskScoreLevel expectedRiskScoreLevel =new RiskScoreLevel(1,"Very low risk",81.0,100.0);
        when(mockRepository.findById(id)).thenReturn(Optional.of(expectedRiskScoreLevel));
        RiskScoreLevel result = service.getRiskScoreLevelById(id);
        assertEquals(expectedRiskScoreLevel, result);
    }

    @Test
    public void testGetRiskScoreLevelById_NotFound() {
        int id = 1;
        RiskScoreLevel expectedRiskScoreLevel =new RiskScoreLevel(1,"Very low risk",81.0,100.0);
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            service.getRiskScoreLevelById(id);
        });
    }

    @Test
    public void testCreateRiskScoreLevel() {
        RiskScoreLevel riskScoreLevel =new RiskScoreLevel(1,"Very low risk",81.0,100.0);
        when(mockRepository.findById(riskScoreLevel.getId())).thenReturn(Optional.empty());
        when(mockRepository.save(riskScoreLevel)).thenReturn(riskScoreLevel);
        RiskScoreLevel result = service.createRiskScoreLevel(riskScoreLevel);
        assertEquals(riskScoreLevel, result);
    }

    @Test
    public void testCreateRiskScoreLevel_AlreadyExists() {
        RiskScoreLevel riskScoreLevel = new RiskScoreLevel(1,"Very low risk",81.0,100.0);
        when(mockRepository.findById(riskScoreLevel.getId())).thenReturn(Optional.of(riskScoreLevel));
        RiskScoreLevel result = service.createRiskScoreLevel(riskScoreLevel);
        assertEquals(riskScoreLevel, result);
        verify(mockRepository,never()).save(any(RiskScoreLevel.class));
    }

    @Test
    public void testUpdateRiskScoreLevel() {
        int id = 1;
        RiskScoreLevel existingRiskScoreLevel = new RiskScoreLevel(1,"Very low risk",81.0,100.0);
        RiskScoreLevel updatedRiskScoreLevel = new RiskScoreLevel(1,"Very low risk",85.0,99.0);
        when(mockRepository.findById(id)).thenReturn(Optional.of(existingRiskScoreLevel));
        when(mockRepository.save(existingRiskScoreLevel)).thenReturn(existingRiskScoreLevel);
        RiskScoreLevel result = service.updateRiskScoreLevel(id, updatedRiskScoreLevel);
        assertEquals(updatedRiskScoreLevel, result);
    }

    @Test
    public void testUpdateRiskScoreLevel_NotFound() {
        int id = 1;
        RiskScoreLevel updatedRiskScoreLevel = new RiskScoreLevel(1,"Very low risk",81.0,100.0);
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            service.updateRiskScoreLevel(id, updatedRiskScoreLevel);
        });
    }

    @Test
    public void testDeleteRiskScoreLevel() {
        int id = 1;
        RiskScoreLevel existingRiskScoreLevel = new RiskScoreLevel(1,"Very low risk",81.0,100.0);
        when(mockRepository.findById(id)).thenReturn(Optional.of(existingRiskScoreLevel));
        boolean result = service.deleteRiskScoreLevel(id);
        assertTrue(result);
        verify(mockRepository, times(1)).delete(existingRiskScoreLevel);
    }

    @Test
    public void testDeleteRiskScoreLevel_NotFound() {
        int id = 1;
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
       assertThrows(NoSuchElementException.class, () -> {
            service.deleteRiskScoreLevel(id);
        });
    }
}

