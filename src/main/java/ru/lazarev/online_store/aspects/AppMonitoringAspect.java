package ru.lazarev.online_store.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.lazarev.online_store.dto.JwtResponse;

import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
public class AppMonitoringAspect {
    // "execution(modifier-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern)
    // throws-pattern?)"
    // execution([модификатор_метода(public, *)?] [тип_возврата] [класс?] [имя_метода]([аргументы]) [исключения?]

    @Before("execution(public void ru.lazarev.online_store.controllers.CartController.addProductToCart(*, *))") // pointcut expression
    public void aopSimpleMethod() {
        System.out.println(new GregorianCalendar().getTime() + " в корзину Добавлен продукт");
    }

    @Before("execution(public void ru.lazarev.online_store.controllers.CartController.*(..))") // pointcut expression
    public void beforeAnyMethodInUserDAOClassWithDetails(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println(new GregorianCalendar().getTime() + " В CartController был вызван метод: " + methodSignature);
        Object[] args = joinPoint.getArgs();
        final String collect = Arrays.stream(args)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .filter(s -> s.length() > 0)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println("Аргументы = " + collect);
    }

    @AfterReturning(
            pointcut = "execution(public * ru.lazarev.online_store.controllers.AuthController.createToken(..))",
            returning = "result")
    public void afterGetBobInfo(JoinPoint joinPoint, ResponseEntity<?> result) {
        final String token = Objects.requireNonNull(result.getBody()).toString().substring(18, 177);
        final String delimiter = String.join("", Collections.nCopies(token.length(), "*"));
        System.out.printf("%s\n%s\n%1$s\n",delimiter, token);
    }


  /*после этого метода токен на фронт почему-то не передается*/

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
