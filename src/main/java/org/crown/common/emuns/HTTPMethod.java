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
package org.crown.common.emuns;

import org.crown.common.exception.UnknownEnumException;

/**
 * HTTP方法枚举
 *
 * @author Caratacus
 */
public enum HTTPMethod {
    GET, POST, PUT, DELETE, PATCH, TRACE, HEAD, OPTIONS;

    /**
     * <p>
     * 获取HTTPMethodEnum
     * </p>
     *
     * @param method 数据库类型字符串
     * @return
     */
    public static HTTPMethod getHttpMethod(String method) {
        HTTPMethod[] httpMethods = HTTPMethod.values();
        for (HTTPMethod httpMethod : httpMethods) {
            if (httpMethod.name().equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        throw new UnknownEnumException("Error: Unknown HTTPMethod, or do not support changing HTTPMethod!\n");
    }

}
