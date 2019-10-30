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
package org.crown.framework.exception;

import javax.servlet.http.HttpServletResponse;

import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ErrorCode;

import com.google.common.base.Throwables;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * MSG 消息异常类
 * </p>
 *
 * @author Caratacus
 */
@Slf4j
public class Crown2Exception extends ApiException {

    private static final long serialVersionUID = 1L;

    public Crown2Exception(int httpCode, String msg, Exception ex) {
        this(httpCode, msg);
        if (httpCode < HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            log.info("Info: MsgException Info {}", ex.getMessage());
        } else {
            log.warn("Warn: MsgException Warn {}", Throwables.getStackTraceAsString(ex));
        }

    }

    public Crown2Exception(int httpCode, String msg) {
        super(ErrorCode.builder().error(ErrorCodeEnum.MSG_EXCEPTION.name()).status(httpCode).msg(msg).build());
    }

}
