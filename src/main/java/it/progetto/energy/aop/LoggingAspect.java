package it.progetto.energy.aop;

import it.progetto.energy.impl.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class LoggingAspect {

    private List<User> userList;
    private User user;

    @Pointcut("execution(public java.util.List<it.progetto.energy.impl.model.User> " +
            "it.progetto.energy.service.UserRuoliService.getAllUser())")
    public void pointCut(){
    }

    @After("pointCut()")
    public void afther(){
        log.info("Dopo del pointcut");
    }

    @Before("pointCut()")
    public void before(JoinPoint jp){
        log.info("Prima del il pointcut + " + jp.getSignature());
    }


}
