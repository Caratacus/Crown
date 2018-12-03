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
package org.crown.common.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.framework.model.ErrorCode;
import org.crown.framework.exception.ApiException;
import org.crown.framework.utils.ResponseUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author Caratacus
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    /**
     * 自定义 REST 业务异常
     *
     * @param request
     * @param response
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public void handleBadRequest(HttpServletRequest request, HttpServletResponse response, ApiException exception) {
        ErrorCode code = exception.getErrorCode();
        ResponseUtils.sendFail(request, response, code);
        if (code.getHttpCode() < HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            log.info("Info: code: {} ,httpCode: {} ,msg: {}", code.getError(), code.getHttpCode(), code.getMsg());
        } else {
            log.warn("Warn: code: {} ,httpCode: {} ,msg: {}", code.getError(), code.getHttpCode(), code.getMsg());
        }
    }

}
