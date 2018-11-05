package org.crown.common.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.crown.common.api.model.ErrorCode;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.api.model.responses.FailureResponses;
import org.crown.common.http.ResponseKit;
import org.crown.common.http.wrapper.ApiResponseWrapper;
import org.crown.common.emuns.ErrorCodeEnum;
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
    public static FailureResponses.FailureResponsesBuilder exceptionMsg(FailureResponses.FailureResponsesBuilder failureResponsesBuilder, Exception exception) {
        if (exception instanceof MethodArgumentNotValidException) {
            StringBuilder builder = new StringBuilder("校验失败:");
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
            allErrors.stream().findFirst().ifPresent(error -> {
                builder.append(((FieldError) error).getField()).append("字段规则为").append(error.getDefaultMessage());
                failureResponsesBuilder.msg(error.getDefaultMessage());
            });
            failureResponsesBuilder.exception(builder.toString());
            return failureResponsesBuilder;
        } else if (exception instanceof MissingServletRequestParameterException) {
            StringBuilder builder = new StringBuilder("参数字段");
            MissingServletRequestParameterException ex = (MissingServletRequestParameterException) exception;
            builder.append(ex.getParameterName());
            builder.append("校验不通过");
            failureResponsesBuilder.exception(builder.toString()).msg(ex.getMessage());
            return failureResponsesBuilder;
        } else if (exception instanceof MissingPathVariableException) {
            StringBuilder builder = new StringBuilder("路径字段");
            MissingPathVariableException ex = (MissingPathVariableException) exception;
            builder.append(ex.getVariableName());
            builder.append("校验不通过");
            failureResponsesBuilder.exception(builder.toString()).msg(ex.getMessage());
            return failureResponsesBuilder;
        } else if (exception instanceof ConstraintViolationException) {
            StringBuilder builder = new StringBuilder("方法.参数字段");
            ConstraintViolationException ex = (ConstraintViolationException) exception;
            ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().stream().findFirst().get();
            builder.append(constraintViolation.getPropertyPath().toString());
            builder.append("校验不通过");
            failureResponsesBuilder.exception(builder.toString()).msg(constraintViolation.getMessage());
            return failureResponsesBuilder;
        }

        failureResponsesBuilder.exception(TypeUtils.castToString(exception));
        return failureResponsesBuilder;
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
        ResponseKit.writeValAsJson(request, getResponseWrapper(response, code), ApiResponses.failure(code, exception));
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

    /**
     * 发送rest错误信息
     *
     * @param request
     * @param response
     * @param codeEnum
     */
    public static void sendRestFail(HttpServletRequest request, HttpServletResponse response, ErrorCodeEnum codeEnum) {
        sendRestFail(request, response, codeEnum.convert(), null);
    }

    /**
     * 发送rest错误信息
     *
     * @param request
     * @param response
     * @param codeEnum
     * @param exception
     */
    public static void sendRestFail(HttpServletRequest request, HttpServletResponse response, ErrorCodeEnum codeEnum,
                                    Exception exception) {
        sendRestFail(request, response, codeEnum.convert(), exception);
    }

    /**
     * 获取Response
     *
     * @return
     */
    public static ApiResponseWrapper getResponseWrapper(HttpServletResponse response, ErrorCode errorCode) {
        return new ApiResponseWrapper(response, errorCode);
    }


}
