package com.example.annotationdemo;

import com.example.annotationdemo.annotation.SafeExecution;
import com.example.annotationdemo.service.CalculatorService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void divideShouldReturnQuotientWhenDenominatorIsNonZero() {
        int result = calculatorService.divide(10, 2);

        assertEquals(5, result);
    }

    @Test
    void divideMethodShouldDeclareSafeExecutionAnnotationWithCorrectFallback() throws NoSuchMethodException {
        Method divideMethod = CalculatorService.class.getMethod("divide", int.class, int.class);
        SafeExecution annotation = divideMethod.getAnnotation(SafeExecution.class);

        assertNotNull(annotation);
        assertEquals(-1, annotation.fallbackValue());
        assertTrue(annotation.description().contains("Division by zero"));
    }
}
