package it.progetto.energy.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class LoggingAspect {

//    @Pointcut("execution(public java.util.List<it.progetto.energy.impl.model.User> " +
//            "it.progetto.energy.service.impl.UserRuoliService.getAllUser())")
//    public void pointCut(){
//    }

//    @After("pointCut()")
//    public void afther(){
//        log.info("Dopo del pointcut");
//    }
//
//    @Before("pointCut()")
//    public void before(JoinPoint jp){
//        log.info("Prima del il pointcut + " + jp.getSignature());
//    }


}
