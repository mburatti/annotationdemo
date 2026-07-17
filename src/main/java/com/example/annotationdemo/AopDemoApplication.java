package com.example.annotationdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopDemoApplication implements CommandLineRunner {

    private final CalculatorService calculatorService;

    AopDemoApplication(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AopDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("--- Test 1: Valid Division ---");
        int successResult = calculatorService.divide(10, 2);
        System.out.println("Result: " + successResult);

        System.out.println("\n--- Test 2: Dangerous Division (By Zero) ---");
        int failedResult = calculatorService.divide(10, 0);
        System.out.println("Result (Recovered): " + failedResult);
    }
}