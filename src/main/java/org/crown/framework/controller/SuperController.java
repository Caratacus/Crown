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
package org.crown.framework.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.framework.responses.ApiResponses;
import org.crown.framework.utils.AntiSQLFilter;
import org.crown.framework.utils.TypeUtils;
import org.crown.common.utils.ApiUtils;
import org.crown.cons.PageCons;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * SuperController
 *
 * @author Caratacus
 */
public class SuperController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 成功的ApiResponses
     *
     * @return
     */
    public static <T> ApiResponses<T> success(T object) {
        return ApiResponses.<T>success(object);
    }

    /**
     * 空的ApiResponses
     *
     * @return
     */
    public static ApiResponses<Void> empty() {
        return ApiResponses.empty();
    }

    /**
     * 获取当前用户id
     */
    public Integer currentUid() {
        return ApiUtils.currentUid();
    }

    /**
     * 获取分页对象
     *
     * @return
     */
    protected <T> Page<T> getPage() {
        return getPage(null);
    }

    /**
     * 获取分页对象
     *
     * @param size
     * @return
     */
    protected <T> Page<T> getPage(Integer size) {
        int index = 1;
        // 页数
        Integer cursor = TypeUtils.castToInt(request.getParameter(PageCons.PAGE_PAGE), index);
        // 分页大小
        Integer limit = Objects.nonNull(size) && size > 0 ? size : TypeUtils.castToInt(request.getParameter(PageCons.PAGE_ROWS), PageCons.DEFAULT_LIMIT);
        // 是否查询分页
        Boolean searchCount = TypeUtils.castToBoolean(request.getParameter(PageCons.SEARCH_COUNT), true);
        limit = limit > PageCons.MAX_LIMIT ? PageCons.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit, searchCount);
        page.setAsc(getParameterSafeValues(PageCons.PAGE_ASCS));
        page.setDesc(getParameterSafeValues(PageCons.PAGE_DESCS));
        return page;
    }

    /**
     * 获取安全参数(SQL ORDER BY 过滤)
     *
     * @param parameter
     * @return
     */
    protected String[] getParameterSafeValues(String parameter) {
        return AntiSQLFilter.getSafeValues(request.getParameterValues(parameter));
    }
}
