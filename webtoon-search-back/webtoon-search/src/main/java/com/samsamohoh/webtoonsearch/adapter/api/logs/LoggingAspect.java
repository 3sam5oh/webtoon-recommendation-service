package com.samsamohoh.webtoonsearch.adapter.api.logs;

import com.samsamohoh.webtoonsearch.adapter.api.webtoon.SelectRecordRequest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.samsamohoh.webtoonsearch.adapter.api.webtoon.SelectRecordController.*(..))")
    public void activeLogPointcut() {}

    /*
    *  Method 실행 후 리턴 될 때마다 active 로그 기록
    * */
    @AfterReturning("activeLogPointcut()")
    public void logAfterReturning(JoinPoint joinPoint) {

        SelectRecordRequest loggingData = (SelectRecordRequest)joinPoint.getArgs()[0];

        MDC.put("time", LocalDateTime.now().toString());
        MDC.put("uid", loggingData.getId());
        MDC.put("gender", loggingData.getGender());
        MDC.put("age", loggingData.getAge());
        MDC.put("url", loggingData.getUrl());
        MDC.put("title", loggingData.getTitle());
        MDC.put("platform", loggingData.getPlatform());
        MDC.put("days", loggingData.getDays());
        MDC.put("isFree", Boolean.toString(loggingData.isFree()));
        MDC.put("isEnd", Boolean.toString(loggingData.isEnd()));

        log.info("User Access Logging");
    }
}
