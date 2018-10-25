package org.crown.common.mybatisplus;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.api.R;

/**
 * <p>
 * QueryEmptyWrapper
 * </p>
 *
 * @author Caratacus
 */
@SuppressWarnings("serial")
public class QueryEmptyWrapper<T> extends QueryWrapper<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public T getEntity() {
        return null;
    }

    @Override
    public String getSqlSelect() {
        return null;
    }

    @Override
    public String getSqlSet() {
        return null;
    }

    @Override
    public MergeSegments getExpression() {
        return null;
    }

    @Override
    public boolean isEmptyOfWhere() {
        return true;
    }

    @Override
    public boolean isEmptyOfNormal() {
        return true;
    }

    @Override
    public boolean nonEmptyOfEntity() {
        return !isEmptyOfEntity();
    }

    @Override
    public boolean isEmptyOfEntity() {
        return true;
    }

    public QueryEmptyWrapper<T> setEntity(T entity) {
        throw new UnsupportedOperationException();
    }

    protected void initEntityClass() {
    }

    protected Class<T> getCheckEntityClass() {
        throw new UnsupportedOperationException();
    }

    @Override
    public QueryEmptyWrapper<T> last(boolean condition, String lastSql) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected QueryEmptyWrapper<T> doIt(boolean condition, ISqlSegment... sqlSegments) {
        throw new UnsupportedOperationException();

    }

    @Override
    public String getParamAlias() {
        return super.getParamAlias();
    }

    @Override
    public String getSqlSegment() {
        return null;
    }


    @Override
    public Map<String, Object> getParamNameValuePairs() {
        return Collections.emptyMap();
    }

    protected String columnsToString(R... columns) {
        return null;
    }

    public QueryEmptyWrapper() {
    }

    @Override
    protected String columnToString(String column) {
        return null;
    }

    @Override
    protected QueryEmptyWrapper<T> instance(AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs) {
        throw new UnsupportedOperationException();
    }

}
