package com.nagarro.riskcalculation.testservice;
import com.nagarro.riskcalculation.model.Output;
import com.nagarro.riskcalculation.repository.OutputRepository;
import com.nagarro.riskcalculation.service.OutputService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OutputServiceTest {
    @InjectMocks
    private OutputService service;
    @Mock
    private OutputRepository mockRepository;

    @Test
    public void testSaveOutput() {
        Map<String,String> formulaOutput=new HashMap<>();
        formulaOutput.put("conduct_weight", "3.90");
        formulaOutput.put("resilience_weight", "18.7");
        formulaOutput.put("total_risk_score", "42.69");
        formulaOutput.put("composite_risk_score", "42.69");
        formulaOutput.put("info_sec_weight", "20.0");
        Output output = new Output("TCS",formulaOutput);
      when(mockRepository.findById(output.getCompanyName()))
                .thenReturn(Optional.empty());
        when(mockRepository.save(output)).thenReturn(output);
        Output result = service.saveOutput(output);
        assertEquals(output, result);
    }

    @Test
    public void testSaveOutput_AlreadyExists() {
        Map<String,String> formulaOutput=new HashMap<>();
        formulaOutput.put("conduct_weight", "3.9000000000000004");
        formulaOutput.put("resilience_weight", "18.799999999999997");
        formulaOutput.put("total_risk_score", "42.699999999999996");
        formulaOutput.put("composite_risk_score", "42.699999999999996");
        formulaOutput.put("info_sec_weight", "20.0");
        Output output = new Output("TCS",formulaOutput);
        when(mockRepository.findById(output.getCompanyName()))
                .thenReturn(Optional.of(output));
        Output result = service.saveOutput(output);
        assertEquals(output, result);
        verify(mockRepository,never()).save(any(Output.class));
    }

    @Test
    public void testGetAllOutput() {
        List<Output> expectedOutputs = new ArrayList<>();
        Map<String,String> formulaOutput1=new HashMap<>();
        formulaOutput1.put("conduct_weight", "3.9000000000000004");
        formulaOutput1.put("resilience_weight", "18.799999999999997");
        formulaOutput1.put("total_risk_score", "42.699999999999996");
        formulaOutput1.put("composite_risk_score", "42.699999999999996");
        formulaOutput1.put("info_sec_weight", "20.0");
        Output output1= new Output("CreditSuisse",formulaOutput1);
        expectedOutputs.add(output1);
        Map<String,String> formulaOutput2=new HashMap<>();
        formulaOutput2.put("conduct_weight", "9.1");
        formulaOutput2.put("resilience_weight","28.2");
        formulaOutput2.put("total_risk_score","69.3");
        formulaOutput2.put("composite_risk_score","69.3");
        formulaOutput2.put("info_sec_weight","32.0");
        Output output2= new Output("Infosys",formulaOutput2);
        expectedOutputs.add(output2);
        when(mockRepository.findAll()).thenReturn(expectedOutputs);
        List<Output> result = service.getAllOutput();
        assertEquals(expectedOutputs, result);
    }

    @Test
    public void testGetAllOutput_EmptyList() {
        List<Output> expectedOutputs = new ArrayList<>();
        when(mockRepository.findAll()).thenReturn(expectedOutputs);
        List<Output> result = service.getAllOutput();
        assertEquals(expectedOutputs, result);
    }

    @Test
    public void testGetAllOutput_NullList() {
        when(mockRepository.findAll()).thenReturn(null);
        List<Output> result = service.getAllOutput();
        Assertions.assertNull(result);
    }
}
