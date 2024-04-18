package com.nagarro.riskcalculation.testservice;
import com.nagarro.riskcalculation.advice.DataAlreadyExistsException;
import com.nagarro.riskcalculation.model.RiskCalcLogic;
import com.nagarro.riskcalculation.repository.RiskCalcLogicRepository;
import com.nagarro.riskcalculation.service.RiskCalcLogicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RiskCalcLogicServiceTest {
    @InjectMocks
    private RiskCalcLogicService service;
    @Mock
    private RiskCalcLogicRepository mockRepository;
    @Test
    public void testGetAllRiskCalcLogics() {
        List<RiskCalcLogic> expectedLogics = new ArrayList<>();
        expectedLogics.add(new RiskCalcLogic(1, "info_sec_weight",
                "Information Security*Information Security's weight"));
        expectedLogics.add(new RiskCalcLogic(2, "resilience_weight",
                "Resilience*Resilience's weight"));
        when(mockRepository.findAll()).thenReturn(expectedLogics);
        List<RiskCalcLogic> result = service.getAllRiskCalcLogics();
        assertEquals(expectedLogics, result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetRiskCalcLogicById() {
        int id = 1;
        RiskCalcLogic expectedLogic = new RiskCalcLogic(1, "info_sec_weight",
                "Information Security*Information Security's weight");
        when(mockRepository.findById(id)).thenReturn(Optional.of(expectedLogic));
        RiskCalcLogic result = service.getRiskCalcLogicById(id);
        assertEquals(expectedLogic, result);
        assertEquals("info_sec_weight",expectedLogic.getElementName());
        assertEquals("Information Security*Information Security's weight",
                expectedLogic.getFormula());
        verify(mockRepository,times(1)).findById(1);
    }
    @Test
    public void testGetRiskCalcLogicById_NotFound() {
        int id = 1;
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.getRiskCalcLogicById(id));
        verify(mockRepository, times(1)).findById(id);
    }


    @Test
    public void testCreateRiskCalcLogic() {
        RiskCalcLogic riskCalcLogic= new RiskCalcLogic(1, "info_sec_weight",
                "Information Security*Information Security's weight");
      when(mockRepository.findById(riskCalcLogic.getId())).thenReturn(Optional.empty());
       when(mockRepository.save(riskCalcLogic)).thenReturn(riskCalcLogic);
        RiskCalcLogic result = service.createRiskCalcLogic(riskCalcLogic);
        assertEquals(riskCalcLogic, result);
        verify(mockRepository,times(1)).save(riskCalcLogic);
    }

@Test
public void testCreateRiskCalcLogic_AlreadyExists() {
    RiskCalcLogic riskCalcLogic = new RiskCalcLogic(1, "info_sec_weight", "Information Security*Information Security's weight");
    when(mockRepository.findByElementName(riskCalcLogic.getElementName())).thenReturn(Optional.of(riskCalcLogic));
    DataAlreadyExistsException exception = assertThrows(DataAlreadyExistsException.class,
            () -> service.createRiskCalcLogic(riskCalcLogic));
    assertEquals("Element Name Already Present!", exception.getMessage());
    verify(mockRepository, times(1)).findByElementName(riskCalcLogic.getElementName());
}

    @Test
    public void testUpdateRiskCalcLogic() {
        int id = 1;
        RiskCalcLogic existingRiskCalcLogic = new RiskCalcLogic(1, "info_sec_weight",
                "Information Security*Information Security's weight");
        RiskCalcLogic updatedRiskCalcLogic = new RiskCalcLogic(1, "resilience_weight",
                "Resilience*Resilience's weight");
        when(mockRepository.findById(id)).thenReturn(Optional.of(existingRiskCalcLogic));
        when(mockRepository.save(existingRiskCalcLogic)).thenReturn(updatedRiskCalcLogic);
        RiskCalcLogic result = service.updateRiskCalcLogic(id, updatedRiskCalcLogic);
        assertEquals(updatedRiskCalcLogic, result);
        verify(mockRepository,times(1)).save(updatedRiskCalcLogic);
    }

    @Test
    public void testUpdateRiskCalcLogic_NotFound() {
        int id = 1;
        RiskCalcLogic updatedRiskCalcLogic = new RiskCalcLogic(1, "resilience_weight",
                "Resilience*Resilience's weight");
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> service.updateRiskCalcLogic(id, updatedRiskCalcLogic));
        verify(mockRepository,never()).save(any(RiskCalcLogic.class));
    }

    @Test
    public void testDeleteRiskCalcLogic() {
        int id = 1;
        RiskCalcLogic existingRiskCalcLogic = new RiskCalcLogic(1, "resilience_weight",
                "Resilience*Resilience's weight");
        when(mockRepository.findById(id)).thenReturn(Optional.of(existingRiskCalcLogic));
        boolean result = service.deleteRiskCalcLogic(id);
        verify(mockRepository).delete(existingRiskCalcLogic);
        assertTrue(result);
    }

    @Test
    public void testDeleteRiskCalcLogic_NotFound() {
        int id = 1;
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> service.deleteRiskCalcLogic(id));
        verify(mockRepository,never()).delete(any(RiskCalcLogic.class));
    }
}
