package org.crown.common.emuns;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.api.model.ErrorCode;
import org.crown.common.exception.UnknownEnumException;


/**
 * 业务异常枚举
 *
 * @author Caratacus
 */
public enum ErrorCodeEnum {

    /**
     * 400
     */
    BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, true, "请求参数错误或不完整"),
    /**
     * 401
     */
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, true, "请先进行认证"),
    /**
     * 403
     */
    FORBIDDEN(HttpServletResponse.SC_FORBIDDEN, true, "无权查看"),
    /**
     * 404
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "未找到该路径"),
    /**
     * 405
     */
    METHOD_NOT_ALLOWED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, true, "请求方式不支持"),
    /**
     * 406
     */
    NOT_ACCEPTABLE(HttpServletResponse.SC_NOT_ACCEPTABLE, true, "不可接受该请求"),
    /**
     * 411
     */
    LENGTH_REQUIRED(HttpServletResponse.SC_LENGTH_REQUIRED, true, "长度受限制"),
    /**
     * 415
     */
    UNSUPPORTED_MEDIA_TYPE(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, true, "不支持的媒体类型"),
    /**
     * 416
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE, true, "不能满足请求的范围"),
    /**
     * 500
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, true, "服务器正在升级，请耐心等待"),
    /**
     * 503
     */
    SERVICE_UNAVAILABLE(HttpServletResponse.SC_SERVICE_UNAVAILABLE, true, "请求超时"),
    //----------------------------------------------------业务异常----------------------------------------------------
    /**
     * 用户名密码错误
     */
    USERNAME_OR_PASSWORD_IS_WRONG(HttpServletResponse.SC_BAD_REQUEST, true, "用户名密码错误"),
    /**
     * 用户被禁用
     */
    USER_IS_DISABLED(HttpServletResponse.SC_NOT_ACCEPTABLE, true, "用户被禁用"),
    /**
     * 未找到该用户
     */
    USER_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "未找到该用户"),
    /**
     * 原密码不正确
     */
    ORIGINAL_PASSWORD_IS_INCORRECT(HttpServletResponse.SC_BAD_REQUEST, true, "原密码不正确"),

    ;

    private final int httpCode;
    private final boolean show;
    private final String msg;

    ErrorCodeEnum(int httpCode, boolean show, String msg) {
        this.httpCode = httpCode;
        this.msg = msg;
        this.show = show;
    }

    /**
     * 转换为ErrorCode(自定义返回消息)
     *
     * @param msg
     * @return
     */
    public ErrorCode convert(String msg) {
        return ErrorCode.builder().httpCode(httpCode()).show(show()).error(name()).msg(msg).build();
    }

    /**
     * 转换为ErrorCode
     *
     * @return
     */
    public ErrorCode convert() {
        return ErrorCode.builder().httpCode(httpCode()).show(show()).error(name()).msg(msg()).build();
    }

    public static ErrorCodeEnum getErrorCode(String errorCode) {
        ErrorCodeEnum[] enums = ErrorCodeEnum.values();
        for (ErrorCodeEnum errorCodeEnum : enums) {
            if (errorCodeEnum.name().equalsIgnoreCase(errorCode)) {
                return errorCodeEnum;
            }
        }
        throw new UnknownEnumException("Error: Unknown errorCode, or do not support changing errorCode!\n");
    }

    public int httpCode() {
        return this.httpCode;
    }

    public String msg() {
        return this.msg;
    }

    public boolean show() {
        return this.show;
    }
}
