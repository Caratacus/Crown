/*
 * Copyright (c) 2018-2019 Caratacus, (caratacus@qq.com).
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
package org.crown.common.framework.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.crown.common.framework.mapper.BaseMapper;
import org.crown.common.framework.model.convert.Convert;
import org.crown.common.framework.service.BaseService;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

/**
 * <p>
 * 基础Service实现 继承于Mybatis-plus
 * </p>
 *
 * @author Caratacus
 */
@Transactional(readOnly = true)
public class BaseServiceImpl<M extends BaseMapper<T>, T extends Convert> implements BaseService<T> {

    @Autowired
    protected M baseMapper;

    /**
     * <p>
     * 判断数据库操作是否成功
     * </p>
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * <p>
     * 批量操作 SqlSession
     * </p>
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(T entity) {
        return retBool(baseMapper.insert(entity));
    }

    /**
     * 批量插入
     *
     * @param entityList
     * @param batchSize
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        //批量对象插入 不存在直接返回true
        if (CollectionUtils.isEmpty(entityList)) {
            return true;
        }
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }

    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal)) {
                    return save(entity);
                } else {
                    /*
                     * 更新成功直接返回，失败执行插入逻辑
                     */
                    return Objects.nonNull(getById((Serializable) idVal)) ? updateById(entity) : save(entity);
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        //批量对象插入 不存在直接返回true
        if (CollectionUtils.isEmpty(entityList)) {
            return true;
        }
        Class<?> cls = currentModelClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        int i = 0;
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            for (T entity : entityList) {
                if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                    Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                    if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                        batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
                    } else {
                        MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                        param.put(Constants.ENTITY, entity);
                        batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
                    }
                    //不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                    i++;
                } else {
                    throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
                }
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        return SqlHelper.delBool(baseMapper.deleteById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean remove(QueryWrapper<T> queryWrapper) {
        return SqlHelper.delBool(baseMapper.delete(queryWrapper));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(T entity) {
        return retBool(baseMapper.updateById(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(T entity, UpdateWrapper<T> updateWrapper) {
        return retBool(baseMapper.update(entity, updateWrapper));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        //批量对象插入 不存在直接返回true
        if (CollectionUtils.isEmpty(entityList)) {
            return true;
        }
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }

    @Override
    public T getById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public T getOne(QueryWrapper<T> queryWrapper) {
        return SqlHelper.getObject(baseMapper.selectList(queryWrapper));
    }

    @Override
    public int count(QueryWrapper<T> queryWrapper) {
        return SqlHelper.retCount(baseMapper.selectCount(queryWrapper));
    }

    @Override
    public List<T> list(QueryWrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<T> page(IPage<T> page, QueryWrapper<T> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public <R> List<R> listObjs(QueryWrapper<T> queryWrapper, Function<? super Object, R> mapper) {
        return baseMapper.selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    @Override
    public <E> IPage<E> pageEntities(IPage page, QueryWrapper wrapper, Function<? super T, E> mapper) {
        return page(page, wrapper).convert(mapper);
    }

    @Override
    public <E extends Convert> E entity(QueryWrapper wrapper, Class<E> cls) {
        E entity = null;
        T t = (T) getOne(wrapper);
        if (Objects.nonNull(t)) {
            entity = t.convert(cls);
        }
        return entity;
    }

    @Override
    public <E> List<E> entitys(QueryWrapper wrapper, Function<? super T, E> mapper) {
        return (List<E>) list(wrapper).stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public <V> Map<Integer, V> list2Map(List<V> list, String property) {
        if (list == null) {
            return Collections.emptyMap();
        }
        Map<Integer, V> map = new LinkedHashMap<>(list.size());
        for (V v : list) {
            Field field = ReflectionUtils.findField(v.getClass(), property);
            if (Objects.isNull(field)) {
                continue;
            }
            ReflectionUtils.makeAccessible(field);
            Object fieldValue = ReflectionUtils.getField(field, v);
            map.put((Integer) fieldValue, v);
        }
        return map;
    }

    @Override
    public Map<Integer, T> list2Map(QueryWrapper<T> wrapper, String property) {
        return list2Map(list(wrapper), property);
    }

}
