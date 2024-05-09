package com.nataly.timetrackingsystem.aspect;

import com.nataly.timetrackingsystem.model.enums.MethodType;
import com.nataly.timetrackingsystem.service.MethodDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TrackTimeAspect {

    private final MethodDataService methodDataService;

    @Pointcut(value = "@annotation(com.nataly.timetrackingsystem.aspect.annotation.TrackTime)")
    void trackSyncTimeMethods() {
    }

    @Around(value = "trackSyncTimeMethods()")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        methodDataService.saveMethodData(executionTime, methodName, className, MethodType.SYNC.name());
        log.info("MethodDto {} from class {} executed synchronously in {} ms", methodName, className, executionTime);
        return result;
    }
}
