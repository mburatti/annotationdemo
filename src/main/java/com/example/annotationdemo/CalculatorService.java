package com.example.annotationdemo;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    @SafeExecution(
        description = "Division by zero detected! Returning fallback value to prevent crash.", 
        fallbackValue = -1
    )
    public int divide(int numerator, int denominator) {
        // This will throw an ArithmeticException if denominator is 0
        return numerator / denominator;
    }
}