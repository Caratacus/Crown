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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.crown.framework.utils.RequestUtils;
import org.springframework.web.util.HtmlUtils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;


/**
 * Request包装类
 * <p>
 * 1.预防xss攻击
 * 2.拓展requestbody无限获取(HttpServletRequestWrapper只能获取一次)
 * </p>
 *
 * @author Caratacus
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * 存储requestBody byte[]
     */
    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        this.body = RequestUtils.getByteBody(request);
    }

    @Override
    public BufferedReader getReader() {
        ServletInputStream inputStream = getInputStream();
        return Objects.isNull(inputStream) ? null : new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public ServletInputStream getInputStream() {
        if (ObjectUtils.isEmpty(body)) {
            return null;
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            @SuppressWarnings("EmptyMethod")
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return bais.read();
            }
        };
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = htmlEscape(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (value instanceof String) {
            htmlEscape((String) value);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    @Override
    public String getQueryString() {
        String value = super.getQueryString();
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    /**
     * 使用spring HtmlUtils 转义html标签达到预防xss攻击效果
     *
     * @param str
     * @see org.springframework.web.util.HtmlUtils#htmlEscape
     */
    protected String htmlEscape(String str) {
        return HtmlUtils.htmlEscape(str);
    }

}