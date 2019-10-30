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
package org.crown.framework.responses;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.crown.framework.model.ErrorCode;
import org.crown.framework.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * GET: 200 OK
 * POST: 201 Created
 * PUT: 200 OK
 * PATCH: 200 OK
 * DELETE: 204 No Content
 * 接口返回(多态)
 *
 * @author Caratacus
 */
@Setter
@Getter
@Accessors(chain = true)
public class ApiResponses<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * http 状态码
     */
    private Integer status;
    /**
     * 描述
     */
    private String msg = "操作成功";

    /**
     * 不需要返回结果
     *
     * @param status
     */
    public static ApiResponses<Void> success(HttpServletResponse response, HttpStatus status) {
        response.setStatus(status.value());
        SuccessResponses<Void> responses = new SuccessResponses<>();
        responses.setStatus(status.value());
        return responses;

    }

    /**
     * 成功返回
     *
     * @param object
     */
    public static <T> ApiResponses<T> success(HttpServletResponse response, T object) {
        return success(response, HttpStatus.OK, object);

    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     */
    public static <T> ApiResponses<T> success(HttpServletResponse response, HttpStatus status, T object) {
        response.setStatus(status.value());
        SuccessResponses<T> responses = new SuccessResponses<>();
        responses.setStatus(status.value());
        responses.setResult(object);
        return responses;
    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     */
    public static <T> ApiResponses<T> success(ServerHttpResponse response, HttpStatus status, T object) {
        response.setStatusCode(status);
        SuccessResponses<T> responses = new SuccessResponses<>();
        responses.setStatus(status.value());
        responses.setResult(object);
        return responses;
    }

    /**
     * 失败返回
     *
     * @param errorCode
     * @param exception
     */
    public static <T> ApiResponses<T> failure(ErrorCode errorCode, Exception exception) {
        FailedResponse failedResponse = new FailedResponse();
        failedResponse.setError(errorCode.getError())
                .setStatus(errorCode.getStatus())
                .setMsg(errorCode.getMsg());
        ResponseUtils.exceptionMsg(failedResponse, exception);
        return failedResponse;
    }

    /**
     * 失败返回
     *
     * @param errorCode
     */
    public static <T> ApiResponses<T> failure(ErrorCode errorCode) {
        FailedResponse failedResponse = new FailedResponse();
        return failedResponse.setError(errorCode.getError())
                .setStatus(errorCode.getStatus())
                .setMsg(errorCode.getMsg());
    }

}
