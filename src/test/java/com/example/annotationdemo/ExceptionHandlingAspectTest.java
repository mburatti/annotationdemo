package com.example.annotationdemo;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionHandlingAspectTest {

    @Test
    void shouldReturnFallbackValueWhenAnnotatedMethodThrowsArithmeticException() {
        ExceptionHandlingAspect aspect = new ExceptionHandlingAspect();
        AspectJProxyFactory factory = new AspectJProxyFactory(new AnnotatedService());
        factory.addAspect(aspect);

        AnnotatedService proxy = factory.getProxy();

        assertEquals(-1, proxy.divide(10, 0));
    }

    @Test
    void shouldReturnNormalResultWhenAnnotatedMethodSucceeds() {
        ExceptionHandlingAspect aspect = new ExceptionHandlingAspect();
        AspectJProxyFactory factory = new AspectJProxyFactory(new AnnotatedService());
        factory.addAspect(aspect);

        AnnotatedService proxy = factory.getProxy();

        assertEquals(5, proxy.divide(10, 2));
    }

    public static class AnnotatedService {
        @SafeExecution(
                description = "Division by zero detected! Returning fallback value to prevent crash.",
                fallbackValue = -1
        )
        public int divide(int numerator, int denominator) {
            return numerator / denominator;
        }
    }
}
