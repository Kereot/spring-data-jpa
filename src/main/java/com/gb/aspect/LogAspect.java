package com.gb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Before("within(com.gb.api..*)")
    public void methodCallLogger(JoinPoint jp) {
        Object[] args = jp.getArgs();
        log.info("Method {}#{} was called with arguments: {}",
                jp.getTarget().getClass().getName(),
                jp.getSignature().getName(),
                args);
    }
}
