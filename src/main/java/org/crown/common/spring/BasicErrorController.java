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

import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.utils.ResponseUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 通用错误处理器
 * </p>
 *
 * @author Caratacus
 */
@SuppressWarnings({"Annotator", "SyntaxError"})
@ApiIgnore
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping
    public void error(HttpServletRequest request,
                      HttpServletResponse response) {
        ErrorCodeEnum errorCode;
        switch (response.getStatus()) {
            case HttpServletResponse.SC_BAD_REQUEST:
                errorCode = ErrorCodeEnum.BAD_REQUEST;
                break;
            case HttpServletResponse.SC_UNAUTHORIZED:
                errorCode = ErrorCodeEnum.UNAUTHORIZED;
                break;
            case HttpServletResponse.SC_FORBIDDEN:
                errorCode = ErrorCodeEnum.FORBIDDEN;
                break;
            case HttpServletResponse.SC_NOT_FOUND:
                errorCode = ErrorCodeEnum.NOT_FOUND;
                break;
            case HttpServletResponse.SC_METHOD_NOT_ALLOWED:
                errorCode = ErrorCodeEnum.METHOD_NOT_ALLOWED;
                break;
            case HttpServletResponse.SC_NOT_ACCEPTABLE:
                errorCode = ErrorCodeEnum.NOT_ACCEPTABLE;
                break;
            case HttpServletResponse.SC_LENGTH_REQUIRED:
                errorCode = ErrorCodeEnum.LENGTH_REQUIRED;
                break;
            case HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE:
                errorCode = ErrorCodeEnum.UNSUPPORTED_MEDIA_TYPE;
                break;
            case HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE:
                errorCode = ErrorCodeEnum.REQUESTED_RANGE_NOT_SATISFIABLE;
                break;
            case HttpServletResponse.SC_SERVICE_UNAVAILABLE:
                errorCode = ErrorCodeEnum.SERVICE_UNAVAILABLE;
                break;
            default:
                errorCode = ErrorCodeEnum.INTERNAL_SERVER_ERROR;
        }
        ResponseUtils.sendFail(request, response, errorCode);
    }

}
