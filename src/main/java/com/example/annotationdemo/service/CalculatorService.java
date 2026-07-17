package com.example.annotationdemo.service;

import com.example.annotationdemo.annotation.SafeExecution;
import jakarta.enterprise.context.ApplicationScoped;

@SafeExecution(
    description = "Division by zero detected! Returning fallback value to prevent crash.",
    fallbackValue = -1
)
@ApplicationScoped
public class CalculatorService {

    public int divide(int numerator, int denominator) {
        try {
            return numerator / denominator;
        } catch (ArithmeticException e) {
            return -1;
        }
    }
}
