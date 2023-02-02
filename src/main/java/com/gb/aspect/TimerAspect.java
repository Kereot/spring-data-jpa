package com.gb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
@Slf4j
public class TimerAspect {

    @Pointcut("@within(com.gb.aspect.Timer)")
    public void annotatedClassPointcut() {}

    @Pointcut("@annotation(Timer)")
    public void annotatedMethodPointcut() {}

    @Pointcut("annotatedClassPointcut() || annotatedMethodPointcut()")
    public void targetPointcut() {}

    @Around("targetPointcut()")
    public Object calculateExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        Instant startTime = Instant.now();
        Object object = pjp.proceed();
        Instant endTime = Instant.now();
        log.info("The duration of method {}#{} execution was: {}ms.",
                pjp.getTarget().getClass().getName(), pjp.getSignature().getName(),
                Duration.between(startTime, endTime).toMillis());
        return object;
    }

//    private String extractMessage(Exception e, ProceedingJoinPoint pjp) {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Timer annoOnMethod = signature.getMethod().getAnnotation(Timer.class);
//        if (annoOnMethod != null) {
//            if (!Objects.equals("null", annoOnMethod.value())) {
//                return annoOnMethod.value();
//            }
//        } else {
//            Class<?> beanClass = pjp.getTarget().getClass();
//            Timer annoOnClass = beanClass.getAnnotation(Timer.class);
//            if (!Objects.equals("null", annoOnClass.value())) {
//                return annoOnClass.value();
//            }
//        }
//        return e.getMessage();
//    }

}
