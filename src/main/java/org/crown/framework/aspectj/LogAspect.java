package org.crown.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.crown.common.annotation.Log;
import org.crown.common.cons.APICons;
import org.crown.common.cons.Constants;
import org.crown.common.utils.JsonUtils;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.manager.ThreadExecutors;
import org.crown.framework.manager.factory.TimerTasks;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.framework.utils.LogUtils;
import org.crown.project.monitor.operlog.domain.OperLog;
import org.crown.project.system.user.domain.User;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志记录处理
 *
 * @author Crown
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    // 配置织入点
    @Pointcut("execution(public * org.crown.project..*.*Controller.*(..))")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     * @param ret       返回值
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        handleLog(joinPoint, ret, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, null, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Object ret, final Exception e) {
        try {
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String actionMethod = className + "." + methodName + "()";
            ApplicationUtils.getRequest().setAttribute(APICons.API_ACTION_METHOD, actionMethod);
            String requestURI = (String) ApplicationUtils.getRequest().getAttribute(APICons.API_REQURL);
            LogUtils.doAfterReturning(ret);
            // 获得注解
            Method method = getMethod(joinPoint);
            Log controllerLog = getAnnotationLog(method);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            User currentUser = ShiroUtils.getSysUser();

            // *========数据库日志=========*//
            OperLog operLog = new OperLog();
            operLog.setStatus(Constants.SUCCESS);
            // 请求的地址
            String ip = ShiroUtils.getIp();
            operLog.setOperIp(ip);
            operLog.setOperUrl(requestURI);
            if (currentUser != null) {
                operLog.setOperName(currentUser.getLoginName());
                if (StringUtils.isNotNull(currentUser.getDept())
                        && StringUtils.isNotEmpty(currentUser.getDept().getDeptName())) {
                    operLog.setDeptName(currentUser.getDept().getDeptName());
                }
            }

            if (e != null) {
                operLog.setStatus(Constants.FAIL);
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            operLog.setMethod(actionMethod);
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            // 保存数据库
            ThreadExecutors.execute(TimerTasks.recordOper(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     */
    public void getControllerMethodDescription(Log log, OperLog operLog) {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog
     */
    private void setRequestValue(OperLog operLog) {
        Map<String, String[]> map = ApplicationUtils.getRequest().getParameterMap();
        String params = JsonUtils.toJson(map);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(Method method) {
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 获取Method
     *
     * @param joinPoint
     * @return
     */
    private Method getMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }
}
