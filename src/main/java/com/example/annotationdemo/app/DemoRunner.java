package com.example.annotationdemo.app;

import com.example.annotationdemo.service.CalculatorService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class DemoRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoRunner.class);

    private final CalculatorService calculatorService;

    @Inject
    public DemoRunner(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public void run(String... args) {
        log.info("--- Test 1: Valid Division ---");
        int successResult = calculatorService.divide(10, 2);
        log.info("Result: {}", successResult);

        log.info("");
        log.info("--- Test 2: Dangerous Division (By Zero) ---");
        int failedResult = calculatorService.divide(10, 0);
        log.info("Result (Recovered): {}", failedResult);
    }
}
