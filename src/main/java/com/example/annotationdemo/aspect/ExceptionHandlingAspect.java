package com.example.annotationdemo.aspect;

import com.example.annotationdemo.annotation.SafeExecution;
import com.example.annotationdemo.util.LogIcon;
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

    @Around("@annotation(safeExecution)")
    public Object handleMathException(ProceedingJoinPoint joinPoint, SafeExecution safeExecution) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (ArithmeticException e) {
            String methodName = joinPoint.getSignature().getName();
            String arguments = Arrays.toString(joinPoint.getArgs());

            log.error(
                "[{} APPLICATION ERROR] | [Location] : {}() | [Inputs]   : {} | [Reason]   : {} | [Detail]   : {}",
                LogIcon.ERROR.getSymbol(),
                methodName,
                arguments,
                safeExecution.description(),
                e.getMessage()
            );

            return safeExecution.fallbackValue();
        }
    }
}
