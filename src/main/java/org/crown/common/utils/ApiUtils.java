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
package org.crown.common.utils;

import java.util.Objects;

import org.crown.common.spring.ApplicationUtils;
import org.crown.cons.APICons;
import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.utils.ApiAssert;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API工具类
 *
 * @author Caratacus
 */
@SuppressWarnings("ALL")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public abstract class ApiUtils {

    /**
     * 获取当前用户id
     */
    public static Integer currentUid() {
        Integer uid = (Integer) ApplicationUtils.getRequest().getAttribute(APICons.API_UID);
        if (Objects.isNull(uid)) {
            String token = ApplicationUtils.getRequest().getHeader("Authorization");
            ApiAssert.notNull(ErrorCodeEnum.UNAUTHORIZED, token);
            token = token.replaceFirst("Bearer ", "");
            Claims claims = JWTUtils.getClaim(token);
            ApiAssert.notNull(ErrorCodeEnum.UNAUTHORIZED, claims);
            return claims.get(JWTUtils.UID, Integer.class);
        }
        return uid;
    }

}
