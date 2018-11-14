package org.crown.common.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.crown.common.framework.model.convert.Convert;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;


/**
 * <p>
 * 基础Service 继承于Mybatis-plus
 * </p>
 *
 * @author Caratacus
 */
public interface BaseService<T extends Convert> {

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
    default boolean saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, batchSize);
    }

    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    boolean saveBatch(Collection<T> entityList, int batchSize);

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
    boolean remove(QueryWrapper<T> queryWrapper);

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
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    boolean update(T entity, UpdateWrapper<T> updateWrapper);

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
    T getOne(QueryWrapper<T> queryWrapper);

    /**
     * <p>
     * 根据 Wrapper，查询一条记录
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default Object getObj(QueryWrapper<T> queryWrapper) {
        return SqlHelper.getObject(listObjs(queryWrapper));
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
    int count(QueryWrapper<T> queryWrapper);

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
    List<T> list(QueryWrapper<T> queryWrapper);

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
     * 翻页查询
     * </p>
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    IPage<T> page(IPage<T> page, QueryWrapper<T> queryWrapper);


    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录
     * </p>
     */
    default List<Object> listObjs() {
        return listObjs(Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    List<Object> listObjs(QueryWrapper<T> queryWrapper);

    /**
     * <p>
     * 翻页查询自定义对象
     * </p>
     *
     * @param page 翻页对象
     * @param cls
     * @return
     */
    default <E> IPage<E> pageEntities(IPage page, Class<E> cls) {
        return pageEntities(page, Wrappers.emptyWrapper(), cls);
    }

    /**
     * <p>
     * 翻页查询自定义对象
     * </p>
     *
     * @param page    翻页对象
     * @param wrapper {@link Wrapper}
     * @param cls
     * @return
     */
    <E> IPage<E> pageEntities(IPage page, QueryWrapper wrapper, Class<E> cls);

    /**
     * <p>
     * 根据 Wrapper，查询一条自定义对象记录
     * </p>
     *
     * @param wrapper {@link Wrapper}
     * @param cls
     * @return
     */
    <E extends Convert> E entity(QueryWrapper wrapper, Class<E> cls);

    /**
     * <p>
     * 查询自定义对象列表
     * </p>
     *
     * @param cls
     * @return
     */
    default <E extends Convert> List<E> entitys(Class<E> cls) {
        return entitys(Wrappers.emptyWrapper(), cls);
    }

    /**
     * <p>
     * 查询自定义对象列表
     * </p>
     *
     * @param wrapper {@link Wrapper}
     * @param cls
     * @return
     */
    <E extends Convert> List<E> entitys(QueryWrapper wrapper, Class<E> cls);

    /**
     * 以list中对象的某个属性做键值,转换成map
     * <p>
     *
     * @param list     要转换的list
     * @param property list中对象的属性,作为键值
     * @return 转换后的map
     */
    <V> Map<Integer, V> list2Map(List<V> list, String property);

    /**
     * 查询list,使用list中对象的某个属性做键值,转换成map
     * <p>
     *
     * @param property list中对象的属性,作为键值
     * @return 转换后的map
     */
    default Map<Integer, T> list2Map(String property) {
        return list2Map(Wrappers.emptyWrapper(), property);
    }

    /**
     * 查询list,使用list中对象的某个属性做键值,转换成map
     * <p>
     *
     * @param wrapper  条件
     * @param property list中对象的属性,作为键值
     * @return 转换后的map
     */
    Map<Integer, T> list2Map(QueryWrapper<T> wrapper, String property);

}
