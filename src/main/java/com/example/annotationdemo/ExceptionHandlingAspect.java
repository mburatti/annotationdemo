package com.example.annotationdemo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class ExceptionHandlingAspect {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    // Intercept any method annotated with @SafeExecution
    @Around("@annotation(safeExecution)")
    public Object handleMathException(ProceedingJoinPoint joinPoint, SafeExecution safeExecution) throws Throwable {
        try {
            // Try to run the actual method
            return joinPoint.proceed();
        } catch (ArithmeticException e) {
            // 1. Capture details about the failure
            String methodName = joinPoint.getSignature().getName();
            String arguments = Arrays.toString(joinPoint.getArgs());
            
            // 2. Log a clean, readable message instead of a stack dump
            log.error(
                "[{} APPLICATION ERROR] | [Location] : {}() | [Inputs]   : {} | [Reason]   : {} | [Detail]   : {}",
                LogIcon.ERROR.getSymbol(),
                methodName,
                arguments,
                safeExecution.description(),
                e.getMessage()
            );

            // 3. Gracefully return the fallback value instead of crashing
            return safeExecution.fallbackValue();
        }
    }
}