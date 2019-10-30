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
package org.crown.common.cons;

/**
 * PAGE 常量
 *
 * @author Caratacus
 */
public interface PageCons {

    /**
     * 当前记录起始索引
     */
    String PAGE_NUM = "_page_num";

    /**
     * 每页显示记录数
     */
    String PAGE_SIZE = "_page_size";
    /**
     * 查询总数
     */
    String SEARCH_COUNT = "searchCount";
    /**
     * 排序列
     */
    String PAGE_SORT = "_page_sort";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    String PAGE_ORDER = "_page_order";

    /**
     * 默认每页条目20
     */
    int DEFAULT_PAGE_SIZE = 20;
    /**
     * 最大条目数100
     */
    int MAX_PAGE_SIZE = 100;

}
