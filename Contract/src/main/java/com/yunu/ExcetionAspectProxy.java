package com.yunu;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 刘江
 * @version 1.0
 * <p>实现异常拦截记录</p>
 */
@Component
@Aspect
public class ExcetionAspectProxy {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @AfterThrowing(throwing = "ex", pointcut ="execution(* com.yunu..*(..))")
    public void recodeException(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackTraceElements= ex.getStackTrace();
        for(StackTraceElement stackTraceElement:stackTraceElements)
        {
            sb.append(stackTraceElement.getClassName()+"    "+stackTraceElement.getFileName()+"    "+stackTraceElement.getMethodName()+"    "+stackTraceElement.getLineNumber());
            sb.append("            ");
        }
        logger.error(sb.toString());
    }
}