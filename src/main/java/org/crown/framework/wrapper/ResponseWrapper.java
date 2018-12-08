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
package org.crown.framework.wrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.crown.common.utils.JacksonUtils;
import org.crown.framework.model.ErrorCode;
import org.crown.framework.responses.ApiResponses;
import org.springframework.util.MimeTypeUtils;

import com.google.common.base.Throwables;

import lombok.extern.slf4j.Slf4j;

/**
 * response包装类
 *
 * @author Caratacus
 */
@Slf4j
public class ResponseWrapper extends HttpServletResponseWrapper {

    private ErrorCode errorcode;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public ResponseWrapper(HttpServletResponse response, ErrorCode errorcode) {
        super(response);
        setErrorCode(errorcode);
    }

    /**
     * 获取ErrorCode
     *
     * @return
     */
    public ErrorCode getErrorCode() {
        return errorcode;
    }

    /**
     * 设置ErrorCode
     *
     * @param errorCode
     */
    public void setErrorCode(ErrorCode errorCode) {
        if (Objects.nonNull(errorCode)) {
            this.errorcode = errorCode;
            super.setStatus(this.errorcode.getHttpCode());
        }
    }

    /**
     * 向外输出错误信息
     *
     * @param e
     * @throws IOException
     */
    public void writerErrorMsg(Exception e) {
        if (Objects.isNull(errorcode)) {
            log.warn("Warn: ErrorCodeEnum cannot be null, Skip the implementation of the method.");
            return;
        }
        printWriterApiResponses(ApiResponses.failure(this.getErrorCode(), e));
    }

    /**
     * 设置ApiErrorMsg
     */
    public void writerErrorMsg() {
        writerErrorMsg(null);
    }

    /**
     * 向外输出ApiResponses
     *
     * @param apiResponses
     */
    public void printWriterApiResponses(ApiResponses apiResponses) {
        writeValueAsJson(apiResponses);
    }

    /**
     * 向外输出json对象
     *
     * @param obj
     */
    public void writeValueAsJson(Object obj) {
        if (super.isCommitted()) {
            log.warn("Warn: Response isCommitted, Skip the implementation of the method.");
            return;
        }
        super.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        super.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = super.getWriter()) {
            writer.print(JacksonUtils.toJson(obj));
            writer.flush();
        } catch (IOException e) {
            log.warn("Error: Response printJson faild, stackTrace: {}", Throwables.getStackTraceAsString(e));
        }
    }

}
