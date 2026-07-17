package com.example.annotationdemo.app;

import com.example.annotationdemo.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoRunner.class);

    private final CalculatorService calculatorService;

    public DemoRunner(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
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
