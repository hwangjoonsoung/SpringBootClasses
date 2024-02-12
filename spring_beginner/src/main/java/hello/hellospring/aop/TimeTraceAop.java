package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring.service..*(..))")//targeting
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        try {
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        }finally {
            long end = System.currentTimeMillis();
            System.out.println("소요시간 : "+ (end -start));
        }
    }
}
