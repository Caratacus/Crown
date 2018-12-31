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
package org.crown.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.crown.common.mybatisplus.LambdaQueryWrapperChain;
import org.crown.common.mybatisplus.LambdaDeleteWrapperChain;
import org.crown.common.mybatisplus.LambdaUpdateWrapperChain;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

/**
 * <p>
 * 基础Service 继承于Mybatis-plus
 * </p>
 *
 * @author Caratacus
 */
public interface BaseService<T> {

    /**
     * 批量大小
     */
    int batchSize = 1024;

    /**
     * <p>
     * 插入一条记录（选择字段，策略插入）
     * </p>
     *
     * @param entity 实体对象
     */
    boolean save(T entity);

    /**
     * <p>p
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象集合
     */
    void saveBatch(Collection<T> entityList);

    /**
     * <p>
     * 批量修改插入
     * </p>
     *
     * @param entityList 实体对象集合
     */
    boolean saveOrUpdateBatch(Collection<T> entityList);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     */
    boolean removeById(Serializable id);

    /**
     * <p>
     * 删除所有记录
     * </p>
     */
    default boolean remove() {
        return remove(Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * 根据 entity 条件，删除记录
     * </p>
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    boolean remove(Wrapper<T> queryWrapper);

    /**
     * <p>
     * 根据 ID 选择修改
     * </p>
     *
     * @param entity 实体对象
     */
    boolean updateById(T entity);

    /**
     * <p>
     * 根据 ID 全部修改
     * </p>
     *
     * @param entity 实体对象
     */
    boolean updateAllColumnById(T entity);

    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    default boolean update(Wrapper<T> updateWrapper) {
        return update(null, updateWrapper);
    }

    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList 实体对象集合
     */
    default boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, batchSize);
    }

    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity 实体对象
     */
    boolean saveOrUpdate(T entity);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     */
    T getById(Serializable id);

    /**
     * <p>
     * 根据 Wrapper，查询一条记录
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default T getOne(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(list(queryWrapper));
    }

    /**
     * <p>
     * 根据 Wrapper，查询一条记录
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default <R> R getObj(Wrapper<T> queryWrapper, Function<? super Object, R> mapper) {
        return SqlHelper.getObject(listObjs(queryWrapper, mapper));
    }

    /**
     * <p>
     * 根据 Wrapper 条件，查询总记录数
     * </p>
     */
    default int count() {
        return count(Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * 根据 Wrapper 条件，查询总记录数
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    int count(Wrapper<T> queryWrapper);

    /**
     * <p>
     * 查询列表
     * </p>
     */
    default List<T> list() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * 查询列表
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    List<T> list(Wrapper<T> queryWrapper);

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page 翻页对象
     */
    default IPage<T> page(IPage<T> page) {
        return page(page, Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录
     * </p>
     */
    default <R> List<R> listObjs(Function<? super Object, R> mapper) {
        return listObjs(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */

    <R> List<R> listObjs(Wrapper<T> queryWrapper, Function<? super Object, R> mapper);

    /**
     * <p>
     * 翻页查询自定义对象
     * </p>
     *
     * @param page   翻页对象
     * @param mapper
     * @return
     */
    default <R> IPage<R> pageEntities(IPage page, Function<? super T, R> mapper) {
        return pageEntities(page, Wrappers.emptyWrapper(), mapper);
    }

    /**
     * <p>
     * 翻页查询自定义对象
     * </p>
     *
     * @param page    翻页对象
     * @param wrapper {@link Wrapper}
     * @param mapper
     * @return
     */
    <R> IPage<R> pageEntities(IPage page, Wrapper<T> wrapper, Function<? super T, R> mapper);

    /**
     * <p>
     * 根据 Wrapper，查询一条自定义对象记录
     * </p>
     *
     * @param wrapper {@link Wrapper}
     * @param mapper
     * @return
     */
    default <R> R entity(Wrapper<T> wrapper, Function<? super T, R> mapper) {
        return SqlHelper.getObject(entitys(wrapper, mapper));
    }

    /**
     * <p>
     * 查询自定义对象列表
     * </p>
     *
     * @param mapper
     * @return
     */
    default <R> List<R> entitys(Function<? super T, R> mapper) {
        return entitys(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * <p>
     * 查询自定义对象列表
     * </p>
     *
     * @param wrapper {@link Wrapper}
     * @param mapper
     * @return
     */
    <R> List<R> entitys(Wrapper<T> wrapper, Function<? super T, R> mapper);

    /**
     * 查询list,使用list中对象的某个属性做键值,转换成map
     * <p>
     *
     * @param column list中对象的属性,作为键值
     * @return 转换后的map
     */
    default <K> Map<K, T> list2Map(SFunction<T, K> column) {
        return list2Map(Wrappers.<T>emptyWrapper(), column);
    }

    /**
     * 查询list,使用list中对象的某个属性做键值,转换成map
     * <p>
     *
     * @param wrapper 条件
     * @param column  list中对象的属性,作为键值
     * @return 转换后的map
     */
    <K> Map<K, T> list2Map(Wrapper<T> wrapper, SFunction<T, K> column);

    default LambdaQueryWrapperChain<T> query() {
        return new LambdaQueryWrapperChain<>(this);
    }

    default LambdaUpdateWrapperChain<T> update() {
        return new LambdaUpdateWrapperChain<>(this);
    }

    default LambdaDeleteWrapperChain<T> delete() {
        return new LambdaDeleteWrapperChain<>(this);
    }

}


