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
package org.crown.framework.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.MapUtils;
import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.exception.ApiException;
import org.crown.framework.model.ErrorCode;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>
 * API 断言
 * </p>
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiAssert {

    /**
     * obj1 eq obj2
     *
     * @param obj1
     * @param obj2
     * @param errorCodeEnum
     */
    public static void equals(ErrorCodeEnum errorCodeEnum, Object obj1, Object obj2) {
        if (!Objects.equals(obj1, obj2)) {
            failure(errorCodeEnum);
        }
    }

    public static void isTrue(ErrorCodeEnum errorCodeEnum, boolean condition) {
        if (!condition) {
            failure(errorCodeEnum);
        }
    }

    public static void isFalse(ErrorCodeEnum errorCodeEnum, boolean condition) {
        if (condition) {
            failure(errorCodeEnum);
        }
    }

    public static void isNull(ErrorCodeEnum errorCodeEnum, Object... conditions) {
        if (ObjectUtils.isNotNull(conditions)) {
            failure(errorCodeEnum);
        }
    }

    public static void notNull(ErrorCodeEnum errorCodeEnum, Object... conditions) {
        if (ObjectUtils.isNull(conditions)) {
            failure(errorCodeEnum);
        }
    }

    /**
     * <p>
     * 失败结果
     * </p>
     *
     * @param errorCodeEnum 异常错误码
     */
    public static void failure(ErrorCodeEnum errorCodeEnum) {
        throw new ApiException(errorCodeEnum);
    }

    public static void notEmpty(ErrorCodeEnum errorCodeEnum, Object[] array) {
        if (ObjectUtils.isEmpty(array)) {
            failure(errorCodeEnum);
        }
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the
     * array is empty!
     * <p>
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
     * </pre>
     *
     * @param array         the array to check
     * @param errorCodeEnum the exception message to use if the assertion fails
     * @throws ApiException if the object array contains a {@code null} element
     */
    public static void noNullElements(ErrorCodeEnum errorCodeEnum, Object[] array) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    failure(errorCodeEnum);
                }
            }
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection    the collection to check
     * @param errorCodeEnum the exception message to use if the assertion fails
     * @throws ApiException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(ErrorCodeEnum errorCodeEnum, Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            failure(errorCodeEnum);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map           the map to check
     * @param errorCodeEnum the exception message to use if the assertion fails
     * @throws ApiException if the map is {@code null} or has no entries
     */
    public static void notEmpty(ErrorCodeEnum errorCodeEnum, Map<?, ?> map) {
        if (MapUtils.isEmpty(map)) {
            failure(errorCodeEnum);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection    the collection to check
     * @param errorCodeEnum the exception message to use if the assertion fails
     * @throws ApiException if the collection is {@code null} or has no elements
     */
    public static void isEmpty(ErrorCodeEnum errorCodeEnum, Collection<?> collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            failure(errorCodeEnum);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map           the map to check
     * @param errorCodeEnum the exception message to use if the assertion fails
     * @throws ApiException if the map is {@code null} or has no entries
     */
    public static void isEmpty(ErrorCodeEnum errorCodeEnum, Map<?, ?> map) {
        if (MapUtils.isNotEmpty(map)) {
            failure(errorCodeEnum);
        }
    }

    /**
     * obj1 eq obj2
     *
     * @param obj1
     * @param obj2
     * @param errorCode
     */
    public static void equals(ErrorCode errorCode, Object obj1, Object obj2) {
        if (!Objects.equals(obj1, obj2)) {
            failure(errorCode);
        }
    }

    public static void isTrue(ErrorCode errorCode, boolean condition) {
        if (!condition) {
            failure(errorCode);
        }
    }

    public static void isFalse(ErrorCode errorCode, boolean condition) {
        if (condition) {
            failure(errorCode);
        }
    }

    public static void isNull(ErrorCode errorCode, Object... conditions) {
        if (ObjectUtils.isNotNull(conditions)) {
            failure(errorCode);
        }
    }

    public static void notNull(ErrorCode errorCode, Object... conditions) {
        if (ObjectUtils.isNull(conditions)) {
            failure(errorCode);
        }
    }

    /**
     * <p>
     * 失败结果
     * </p>
     *
     * @param errorCode 异常错误码
     */
    public static void failure(ErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    public static void notEmpty(ErrorCode errorCode, Object[] array) {
        if (ObjectUtils.isEmpty(array)) {
            failure(errorCode);
        }
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the
     * array is empty!
     * <p>
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
     * </pre>
     *
     * @param array     the array to check
     * @param errorCode the exception message to use if the assertion fails
     * @throws ApiException if the object array contains a {@code null} element
     */
    public static void noNullElements(ErrorCode errorCode, Object[] array) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    failure(errorCode);
                }
            }
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @param errorCode  the exception message to use if the assertion fails
     * @throws ApiException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(ErrorCode errorCode, Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            failure(errorCode);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map       the map to check
     * @param errorCode the exception message to use if the assertion fails
     * @throws ApiException if the map is {@code null} or has no entries
     */
    public static void notEmpty(ErrorCode errorCode, Map<?, ?> map) {
        if (MapUtils.isEmpty(map)) {
            failure(errorCode);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @param errorCode  the exception message to use if the assertion fails
     * @throws ApiException if the collection is {@code null} or has no elements
     */
    public static void isEmpty(ErrorCode errorCode, Collection<?> collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            failure(errorCode);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map       the map to check
     * @param errorCode the exception message to use if the assertion fails
     * @throws ApiException if the map is {@code null} or has no entries
     */
    public static void isEmpty(ErrorCode errorCode, Map<?, ?> map) {
        if (MapUtils.isNotEmpty(map)) {
            failure(errorCode);
        }
    }

}
