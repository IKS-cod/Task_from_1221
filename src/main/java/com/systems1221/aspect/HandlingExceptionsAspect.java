package com.systems1221.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HandlingExceptionsAspect {
    private static final Logger logger = LoggerFactory.getLogger(HandlingExceptionsAspect.class);

    @AfterThrowing(pointcut = "@annotation(com.systems1221.aspect.annotation.CustomExceptionHandling)",
            throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.error("Method {} has thrown an exception: {}",
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }
}
