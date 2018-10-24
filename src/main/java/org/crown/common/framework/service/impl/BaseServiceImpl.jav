package org.crown.common.framework.service.impl;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.crown.common.framework.mapper.BaseMapper;
import org.crown.common.framework.service.BaseService;
import org.crown.common.kit.BeanConverter;
import org.springframework.util.ReflectionUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import springfox.documentation.schema.property.BaseModelProperty;


/**
 * <p>
 * 基础Service实现 继承于Mybatis-plus
 * </p>
 *
 * @author Caratacus
 * @since 2018-04-06
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseConvert> implements BaseService<T extends BaseConvert> {

    @Override
    public <E> IPage<E> selectEntityPage(IPage page, Wrapper wrapper, Class<E> cls) {
        page(page, wrapper);
        page.setRecords(BeanConverter.convert(cls, page.getRecords()));
        return page;
    }

    @Override
    public <E extends BaseConvert> E selectEntity(Wrapper wrapper, Class<E> cls) {
        E entity = null;
        T t = (T) getOne(wrapper);
        if (Objects.nonNull(t)) {
            entity = t.convert(cls);
        }
        return entity;
    }

    @Override
    public <E extends BaseConvert> List<E> selectEntitys(Wrapper wrapper, Class<E> cls) {
        List<E> entitys = Collections.emptyList();
        List<T> list = list(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            entitys = BeanConverter.convert(cls, list);
        }
        return entitys;
    }


    @Override
    public <V> Map<Integer, V> list2Map(List<V> list, String property) {
        if (list == null) {
            return Collections.emptyMap();
        }
        Map<Integer, V> map = new LinkedHashMap<>(list.size());
        for (V v : list) {
            Field field = ReflectionUtils.findField(v.getClass(), property);
            ReflectionUtils.makeAccessible(field);
            Object fieldValue = ReflectionUtils.getField(field, v);
            map.put((Integer) fieldValue, v);
        }
        return map;
    }

    @Override
    public Map<Integer, T> list2Map(Wrapper wrapper, String property) {
        return list2Map(list(wrapper), property);
    }

}
