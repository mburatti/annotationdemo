package com.example.annotationdemo;

import com.example.annotationdemo.annotation.SafeExecution;
import com.example.annotationdemo.aspect.ExceptionHandlingAspect;
import jakarta.interceptor.InvocationContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ExceptionHandlingAspectTest {

    @Test
    void shouldReturnFallbackValueWhenAnnotatedMethodThrowsArithmeticException() throws Exception {
        ExceptionHandlingAspect aspect = new ExceptionHandlingAspect();
        InvocationContext context = Mockito.mock(InvocationContext.class);
        AnnotatedService service = new AnnotatedService();
        Method method = AnnotatedService.class.getMethod("divide", int.class, int.class);

        when(context.getMethod()).thenReturn(method);
        when(context.getParameters()).thenReturn(new Object[]{10, 0});
        when(context.proceed()).thenThrow(new ArithmeticException("/ by zero"));

        assertEquals(-1, aspect.handleMathException(context));
    }

    @Test
    void shouldReturnNormalResultWhenAnnotatedMethodSucceeds() throws Exception {
        ExceptionHandlingAspect aspect = new ExceptionHandlingAspect();
        InvocationContext context = Mockito.mock(InvocationContext.class);
        AnnotatedService service = new AnnotatedService();
        Method method = AnnotatedService.class.getMethod("divide", int.class, int.class);

        when(context.getMethod()).thenReturn(method);
        when(context.getParameters()).thenReturn(new Object[]{10, 2});
        when(context.proceed()).thenReturn(5);

        assertEquals(5, aspect.handleMathException(context));
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
