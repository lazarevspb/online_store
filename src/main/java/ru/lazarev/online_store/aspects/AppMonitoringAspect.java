package ru.lazarev.online_store.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class AppMonitoringAspect {
    private int tokenRequests = 0;
    private int callToTheCartMethod = 0;

    @Before("execution(public void ru.lazarev.online_store.controllers.CartController.updateCart(*, *))")
    // pointcut expression
    public void aopSimpleMethod(JoinPoint joinPoint) {
        callToTheCartMethod++;
        log.info(callToTheCartMethod + " раз корзина обновлена");
    }

    @Before("execution(public void ru.lazarev.online_store.controllers.CartController.*(..))") // pointcut expression
    public void beforeAnyMethodInUserDAOClassWithDetails(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info(" В CartController был вызван метод: " + methodSignature);
        Object[] args = joinPoint.getArgs();
        final String collect = Arrays.stream(args)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .filter(s -> s.length() > 0)
                .collect(Collectors.joining(",", "[", "]"));
        log.info("Аргументы = " + collect);
    }

    @AfterReturning(
            pointcut = "execution(public * ru.lazarev.online_store.controllers.AuthController.createToken(..))",
            returning = "result")
    public void afterGetBobInfo(JoinPoint joinPoint, ResponseEntity<?> result) {

        final String token = Objects.requireNonNull(result.getBody()).toString().substring(18, 177);
        final String delimiter = String.join("", Collections.nCopies(token.length(), "*"));
        tokenRequests++;
        log.info(String.format("%3$d раз запрошен токен\n%s\n%s\n%1$s\n", delimiter, token, tokenRequests));
    }


    /*после этого метода токен на фронт почему-то не передается:(*/

//    @Around("execution(public * ru.lazarev.online_store.controllers.*.*(..)))")
//    public void methodProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        System.out.println("start profiling");
//        long begin = System.currentTimeMillis();
//        proceedingJoinPoint.proceed();
//        long end = System.currentTimeMillis();
//        long duration = end - begin;
//
//        System.out.println((MethodSignature) proceedingJoinPoint.getSignature() + " duration: " + duration);
//        System.out.println("end profiling");
//    }


}
