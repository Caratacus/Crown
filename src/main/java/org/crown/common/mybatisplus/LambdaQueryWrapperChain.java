package org.crown.common.mybatisplus;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.crown.framework.service.BaseService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.additional.AbstractChainWrapper;

public class LambdaQueryWrapperChain<T> extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaQueryWrapperChain<T>, LambdaQueryWrapper<T>>
        implements Query<LambdaQueryWrapperChain<T>, T, SFunction<T, ?>> {

    private BaseService<T> baseService;

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

    public IPage<T> page(IPage<T> page) {
        return baseService.page(page, getWrapper());
    }

    public boolean remove() {
        return baseService.remove(getWrapper());
    }

    public <R> R getObj(Function<? super Object, R> mapper) {
        return baseService.getObj((Wrapper<T>) getWrapper(), mapper);
    }

    public <R> IPage<R> pageEntities(IPage page, Function<? super T, R> mapper) {
        return baseService.pageEntities(page, getWrapper(), mapper);
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