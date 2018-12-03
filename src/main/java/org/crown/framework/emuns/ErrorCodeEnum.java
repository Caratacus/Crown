/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.framework.emuns;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.exception.UnknownEnumException;
import org.crown.framework.model.ErrorCode;

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
     * JSON格式错误
     */
    JSON_FORMAT_ERROR(HttpServletResponse.SC_BAD_REQUEST, true, "JSON格式错误"),
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
    /**
     * 演示系统，无法该操作
     */
    DEMO_SYSTEM_CANNOT_DO(HttpServletResponse.SC_SERVICE_UNAVAILABLE, true, "演示系统，无法该操作"),
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
    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXISTS(HttpServletResponse.SC_BAD_REQUEST, true, "用户名已存在"),
    /**
     * 未找到该菜单
     */
    MENU_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "未找到该菜单"),

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
