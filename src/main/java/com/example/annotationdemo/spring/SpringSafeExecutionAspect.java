package com.example.annotationdemo.spring;

import com.example.annotationdemo.annotation.SafeExecution;
import com.example.annotationdemo.util.LogIcon;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class SpringSafeExecutionAspect {

    private static final Logger log = LoggerFactory.getLogger(SpringSafeExecutionAspect.class);

    @Around("@within(com.example.annotationdemo.annotation.SafeExecution) || @annotation(com.example.annotationdemo.annotation.SafeExecution)")
    public Object handleSafeExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SafeExecution safeExecution = method.getAnnotation(SafeExecution.class);
        if (safeExecution == null) {
            safeExecution = joinPoint.getTarget().getClass().getAnnotation(SafeExecution.class);
        }

        log.info("Spring AOP intercepting method {} with SafeExecution, annotation present={}", method.getName(), safeExecution != null);
        try {
            return joinPoint.proceed();
        } catch (ArithmeticException e) {
            if (safeExecution == null) {
                log.error("SafeExecution annotation is missing on intercepted method {}", method.getName());
                throw e;
            }
            String arguments = Arrays.toString(joinPoint.getArgs());
            log.error(
                    "[{} APPLICATION ERROR] | [Location] : {}() | [Inputs]   : {} | [Reason]   : {} | [Detail]   : {}",
                    LogIcon.ERROR.getSymbol(),
                    method.getName(),
                    arguments,
                    safeExecution.description(),
                    e.getMessage()
            );
            return safeExecution.fallbackValue();
        }
    }
}
