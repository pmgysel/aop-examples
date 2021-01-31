package com.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Aspects implements Ordered {

  @Before("@annotation(com.example.aop.LogMethodName)")
  public void logMethodName(JoinPoint joinPoint) {
    String method = joinPoint.getSignature().getName();
    String params = Arrays.toString(joinPoint.getArgs());
    System.out.println("Method [" + method + "] gets called with parameters " + params);
  }

  @Around("@annotation(com.example.aop.MonitorTime)")
  public Object monitorTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long duration = System.currentTimeMillis() - startTime;
    System.out.println("Execution took [" + duration + "ms]");
    return proceed;
  }

  @Around("@annotation(com.example.aop.RetryOperation)")
  public Object doIdempotentOperation(ProceedingJoinPoint joinPoint) throws Throwable {
    int numAttempts = 0;
    RuntimeException exception;
    do {
      try {
        return joinPoint.proceed();
      } catch(RuntimeException e) {
        numAttempts++;
        exception = e;
      }
    } while(numAttempts <= 100);
    throw exception;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
