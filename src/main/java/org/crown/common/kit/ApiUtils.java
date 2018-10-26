package org.crown.common.kit;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.crown.common.api.model.ErrorCode;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.alibaba.fastjson.util.TypeUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


/**
 * <p>
 * ApiUtils Api接口返回相关工具类
 * </p>
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ApiUtils {


    /**
     * 获取异常信息
     *
     * @param exception
     * @return
     */
    private static String exceptionMsg(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException) {
            StringBuilder builder = new StringBuilder("校验失败:");
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
            allErrors.stream().findFirst().ifPresent(error -> builder.append(((FieldError) error).getField()).append("字段规则为").append(error.getDefaultMessage()));
            return builder.toString();
        } else if (exception instanceof MissingServletRequestParameterException) {
            StringBuilder builder = new StringBuilder("参数字段");
            MissingServletRequestParameterException ex = (MissingServletRequestParameterException) exception;
            builder.append(ex.getParameterName());
            builder.append("校验不通过");
            return builder.toString();
        } else if (exception instanceof MissingPathVariableException) {
            StringBuilder builder = new StringBuilder("路径字段");
            MissingPathVariableException ex = (MissingPathVariableException) exception;
            builder.append(ex.getVariableName());
            builder.append("校验不通过");
            return builder.toString();
        } else if (exception instanceof ConstraintViolationException) {
            StringBuilder builder = new StringBuilder("方法.参数字段");
            ConstraintViolationException ex = (ConstraintViolationException) exception;
            ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().stream().findFirst().get();
            builder.append(constraintViolation.getPropertyPath().toString());
            builder.append("校验不通过");
            return builder.toString();
        }
        return TypeUtils.castToString(exception);
    }


    /**
     * 发送rest错误信息
     *
     * @param request
     * @param response
     * @param code
     */
    public static void sendRestFail(HttpServletRequest request, HttpServletResponse response, ErrorCode code,
                                    Exception exception) {
        //TODO
        //  OutputUtils.printApiJson(request, getResponse(response, code), ApiUtils.failResult(code, exception));
    }


    /**
     * 发送rest错误信息
     *
     * @param request
     * @param response
     * @param code
     */
    public static void sendRestFail(HttpServletRequest request, HttpServletResponse response, ErrorCode code) {
        sendRestFail(request, response, code, null);
    }


}
