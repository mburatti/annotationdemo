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
        var method = context.getMethod();
        SafeExecution safeExecution = method.getAnnotation(SafeExecution.class);
        if (safeExecution == null && context.getTarget() != null) {
            try {
                var targetMethod = context.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
                safeExecution = targetMethod.getAnnotation(SafeExecution.class);
            } catch (NoSuchMethodException ignored) {
                // Fall back to original method annotation if not found on the target class.
            }
        }
        log.info("Intercepting method {} with SafeExecution, annotation present={}", method.getName(), safeExecution != null);
        try {
            return context.proceed();
        } catch (ArithmeticException e) {
            if (safeExecution == null) {
                log.error("SafeExecution annotation is missing on intercepted method {}", method.getName());
                throw e;
            }
            String methodName = method.getName();
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
