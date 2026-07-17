package com.example.annotationdemo;

import com.example.annotationdemo.service.CalculatorService;
import com.example.annotationdemo.app.DemoRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DemoRunnerTest {

    @Mock
    private CalculatorService calculatorService;

    @Test
    void runShouldInvokeDivisionForBothSuccessAndFailurePaths() throws Exception {
        when(calculatorService.divide(10, 2)).thenReturn(5);
        when(calculatorService.divide(10, 0)).thenReturn(-1);

        DemoRunner runner = new DemoRunner(calculatorService);
        runner.run();

        verify(calculatorService).divide(10, 2);
        verify(calculatorService).divide(10, 0);
    }
}
