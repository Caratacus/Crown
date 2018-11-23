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
package org.crown.common.api.model.responses;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.crown.common.api.ApiUtils;
import org.crown.common.api.model.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 接口返回(多态)
 *
 * @author Caratacus
 */
public class ApiResponses<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 不需要返回结果
     */
    public static ApiResponses<Void> empty() {
        return SuccessResponses.<Void>builder().status(HttpStatus.OK.value()).build();

    }

    /**
     * 成功返回
     *
     * @param object
     */
    public static <T> ApiResponses<T> success(T object) {
        return SuccessResponses.<T>builder().status(HttpStatus.OK.value()).result(object).build();

    }

    /**
     * 失败返回
     *
     * @param errorCode
     * @param exception
     */
    public static <T> ApiResponses<T> failure(ErrorCode errorCode, Exception exception) {
        return ApiUtils.exceptionMsg(FailureResponses.builder().msg(errorCode.getMsg()), exception)
                .error(errorCode.getError())
                .show(errorCode.isShow())
                .time(LocalDateTime.now())
                .status(errorCode.getHttpCode())
                .build();
    }

}
