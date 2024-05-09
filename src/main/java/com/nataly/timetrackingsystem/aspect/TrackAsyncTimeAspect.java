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

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TrackAsyncTimeAspect {

    private final MethodDataService methodDataService;

    @Pointcut(value = "@annotation(com.nataly.timetrackingsystem.aspect.annotation.TrackAsyncTime)")
    void trackAsyncTimeMethods() {
    }

    @Around(value = "trackAsyncTimeMethods()")
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint) {
        CompletableFuture<Object> future = new CompletableFuture<>();
        long startTime = System.currentTimeMillis();
        CompletableFuture.runAsync(() -> {
            try {
                Object result = joinPoint.proceed();
                future.complete(result);
            } catch (Throwable e) {
                future.completeExceptionally(e);
            }
        });
        long executionTime = System.currentTimeMillis() - startTime;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        methodDataService.saveMethodData(executionTime, methodName, className, MethodType.ASYNC.name());
        log.info("MethodDto {} from class {} executed asynchronously in {} ms", methodName, className, executionTime);
        return future;
    }
}
