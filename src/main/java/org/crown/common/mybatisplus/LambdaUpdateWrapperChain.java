package org.crown.common.mybatisplus;

import org.crown.framework.service.BaseService;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.additional.AbstractChainWrapper;

public class LambdaUpdateWrapperChain<T> extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaUpdateWrapperChain<T>, LambdaUpdateWrapper<T>> implements Update<LambdaUpdateWrapperChain<T>, SFunction<T, ?>> {

    private BaseService<T> baseService;

    public LambdaUpdateWrapperChain(BaseService<T> baseService) {
        super();
        this.baseService = baseService;
        super.wrapperChildren = new LambdaUpdateWrapper<>();
    }

    @Override
    public LambdaUpdateWrapperChain<T> set(boolean condition, SFunction<T, ?> column, Object val) {
        wrapperChildren.set(condition, column, val);
        return typedThis;
    }

    @Override
    public LambdaUpdateWrapperChain<T> setSql(boolean condition, String sql) {
        wrapperChildren.setSql(condition, sql);
        return typedThis;
    }

    @Override
    public String getSqlSet() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSet");
    }

    public boolean update(T entity) {
        return baseService.update(entity, getWrapper());
    }

    public boolean update() {
        return baseService.update(getWrapper());
    }
}