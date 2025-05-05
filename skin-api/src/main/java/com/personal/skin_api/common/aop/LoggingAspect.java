package com.personal.skin_api.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // 컨트롤러 or 서비스 패키지 기준으로 지정
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerBean() {}

    @Around("controllerBean()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("➡️ [REQUEST] {} with args = {}", methodName, Arrays.toString(args));

        Object result = joinPoint.proceed();
        log.info("✅ [RESPONSE] {} returned = {}", methodName, result);
        return result;
    }
}