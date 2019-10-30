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
package org.crown.common.mybatisplus;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.crown.framework.service.BaseService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.additional.AbstractChainWrapper;

/**
 * Lambda查询Wrapper
 *
 * @author Caratacus
 */
public class LambdaQueryWrapperChain<T> extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaQueryWrapperChain<T>, LambdaQueryWrapper<T>>
        implements Query<LambdaQueryWrapperChain<T>, T, SFunction<T, ?>> {

    private final BaseService<T> baseService;

    public LambdaQueryWrapperChain(BaseService<T> baseService) {
        super();
        this.baseService = baseService;
        super.wrapperChildren = new LambdaQueryWrapper<>();
    }

    @SafeVarargs
    @Override
    public final LambdaQueryWrapperChain<T> select(SFunction<T, ?>... columns) {
        wrapperChildren.select(columns);
        return typedThis;
    }

    @Override
    public LambdaQueryWrapperChain<T> select(Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(predicate);
        return typedThis;
    }

    @Override
    public LambdaQueryWrapperChain<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(entityClass, predicate);
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    public List<T> list() {
        return baseService.list(getWrapper());
    }

    public <R> List<R> listObjs(Function<? super Object, R> mapper) {
        return baseService.listObjs(getWrapper(), mapper);
    }

    public T getOne() {
        return (T) baseService.getOne(getWrapper());
    }

    public Integer count() {
        return baseService.count(getWrapper());
    }

    public boolean exist() {
        return baseService.exist(getWrapper());
    }

    public boolean nonExist() {
        return baseService.nonExist(getWrapper());
    }

    public <R> R getObj(Function<? super Object, R> mapper) {
        return baseService.getObj((Wrapper<T>) getWrapper(), mapper);
    }

    public <R> R entity(Function<? super T, R> mapper) {
        return baseService.entity((Wrapper<T>) getWrapper(), mapper);
    }

    public <R> List<R> entitys(Function<? super T, R> mapper) {
        return baseService.entitys(getWrapper(), mapper);
    }

    public <K> Map<K, T> list2Map(SFunction<T, K> column) {
        return baseService.list2Map(getWrapper(), column);
    }

}