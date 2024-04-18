package com.nagarro.riskcalculation.testcontroller;
import com.nagarro.riskcalculation.controller.VariableController;
import com.nagarro.riskcalculation.service.VariableService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
public class VariableControllerTest {

    @Mock
    private VariableService variableService;

    @InjectMocks
    private VariableController variableController;

    private Set<String> mockedVariables;

    @Test
    public void testGetAllVariables() {
        mockedVariables = new HashSet<>();
        mockedVariables.add("test1");
        mockedVariables.add("test2");
        when(variableService.getAllVariable()).thenReturn(mockedVariables);
        ResponseEntity<Set<String>> responseEntity = variableController.getAllVariables();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockedVariables, responseEntity.getBody());
        verify(variableService, times(1)).getAllVariable();
    }
}
