package org.crown.common.mybatisplus;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

/**
 * <p>
 * Wrapper 条件构造
 * </p>
 *
 * @author Caratacus
 */
public class Wrappers implements Serializable {

    private static final QueryWrapper queryEmptyWrapper = new QueryEmptyWrapper<>();

    public static <T> QueryWrapper<T> query() {
        return new QueryWrapper<>();
    }

    public static <T> UpdateWrapper<T> update() {
        return new UpdateWrapper<>();
    }

    public static <T> QueryWrapper<T> query(T entity) {
        return new QueryWrapper<>(entity);
    }

    public static <T> UpdateWrapper<T> update(T entity) {
        return new UpdateWrapper<>(entity);
    }

    public static <T> QueryWrapper<T> emptyQueryWrapper() {
        return (QueryWrapper<T>) queryEmptyWrapper;
    }
}
