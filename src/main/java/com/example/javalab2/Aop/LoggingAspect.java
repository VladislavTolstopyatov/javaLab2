package com.example.javalab2.Aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    // Логирование всех методов сервисов
    @Pointcut("execution(* com.example.javalab2.service..*(..)))")
    public void anyServiceMethods() {
    }

    @Before("anyServiceMethods()")
    public void beforeAnyServiceMethod(JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String methodName = methodSignature.getMethod().getName();
        final Object[] arguments = joinPoint.getArgs();

        if (arguments.length > 0) {
            log.info(String.format("Call Service method %s with args %s, callTime: %s",
                    methodName, Arrays.toString(arguments), LocalDate.now()));
        } else {
            log.info(String.format("Call Service method %s, callTime: %s", methodName, LocalDate.now()));
        }
    }
}
