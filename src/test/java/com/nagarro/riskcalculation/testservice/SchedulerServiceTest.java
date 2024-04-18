package com.nagarro.riskcalculation.testservice;
import com.nagarro.riskcalculation.service.FormulaEvaluatorService;
import com.nagarro.riskcalculation.service.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SchedulerServiceTest {

    @Mock
    private FormulaEvaluatorService mockFormulaEvaluatorService;
    @InjectMocks
    private SchedulerService schedulerService;

    @Test
    public void testEvaluateFormulasByScheduling() {
        schedulerService.evaluateFormulasByScheduling();
        verify(mockFormulaEvaluatorService, times(1))
                .evaluateFormulas("Scheduler");
    }
}
