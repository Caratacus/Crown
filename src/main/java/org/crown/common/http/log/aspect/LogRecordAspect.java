package org.crown.common.http.log.aspect;


import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.crown.common.http.log.LogHelper;


/**
 * Controller统一切点日志处理
 */
@Aspect
public class LogRecordAspect {

    @Pointcut("execution(public * org.crown.controller.*RestController.*(..))")
    public void pointCut() {
    }

    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public void doAfterReturning(Object ret) {
        LogHelper.doAfterReturning(ret);
    }

}