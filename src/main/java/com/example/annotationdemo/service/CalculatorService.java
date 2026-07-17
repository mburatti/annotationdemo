package com.example.annotationdemo.service;

import com.example.annotationdemo.annotation.SafeExecution;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalculatorService {

    @SafeExecution(
        description = "Division by zero detected! Returning fallback value to prevent crash.",
        fallbackValue = -1
    )
    public int divide(int numerator, int denominator) {
        return numerator / denominator;
    }
}
