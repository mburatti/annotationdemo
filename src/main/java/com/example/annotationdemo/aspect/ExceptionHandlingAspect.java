package com.example.annotationdemo.aspect;

import com.example.annotationdemo.annotation.SafeExecution;
import com.example.annotationdemo.util.LogIcon;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@SafeExecution
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ExceptionHandlingAspect {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @AroundInvoke
    public Object handleMathException(InvocationContext context) throws Exception {
        SafeExecution safeExecution = context.getMethod().getAnnotation(SafeExecution.class);
        try {
            return context.proceed();
        } catch (ArithmeticException e) {
            String methodName = context.getMethod().getName();
            String arguments = Arrays.toString(context.getParameters());

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
