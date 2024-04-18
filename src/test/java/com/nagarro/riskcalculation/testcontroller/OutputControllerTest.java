package com.nagarro.riskcalculation.testcontroller;
import com.nagarro.riskcalculation.controller.OutputController;
import com.nagarro.riskcalculation.model.Output;
import com.nagarro.riskcalculation.service.OutputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
class OutputControllerTest {

    @Mock
    private OutputService outputService;

    @InjectMocks
    private OutputController outputController;

    @Test
    void testSaveOutput() {
        Map<String,String> mapOutput=new HashMap<>();
        mapOutput.put("resilience weight","10");
        mapOutput.put("conduct weight","20");
        mapOutput.put("info_sec_weight","30");
        // Mock data
        Output output =new Output("TCS",mapOutput);
        when(outputService.saveOutput(output)).thenReturn(output);
        // Execute the method
        ResponseEntity<Output> response = outputController.saveOutput(output);
        // Verify the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(output, response.getBody());
        verify(outputService, times(1)).saveOutput(output);
    }

    @Test
    void testGetAllOutput() {
        Map<String,String> output1=new HashMap<>();
        output1.put("resilience weight","10");
        output1.put("conduct weight","20");
        output1.put("info_sec_weight","30");
        Map<String,String> output2=new HashMap<>();
        output1.put("resilience weight","10");
        output1.put("conduct weight","20");
        output1.put("info_sec_weight","30");
        // Mock data
        List<Output> outputs = new ArrayList<>();
        outputs.add(new Output("TCS", output1));
        outputs.add(new Output("Infosys", output2));
        when(outputService.getAllOutput()).thenReturn(outputs);
        // Execute the method
        ResponseEntity<List<Output>> response = outputController.getAllOutput();
        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputs, response.getBody());
        verify(outputService, times(1)).getAllOutput();
    }
}
