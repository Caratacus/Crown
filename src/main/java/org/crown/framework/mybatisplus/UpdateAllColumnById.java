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
package org.crown.framework.mybatisplus;

import static java.util.stream.Collectors.joining;

import java.util.Objects;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;

/**
 * <p>
 * 根据 ID 更新所有字段
 * </p>
 *
 * @author Caratacus
 */
public class UpdateAllColumnById extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                sqlSet(false, tableInfo, ENTITY_DOT),
                tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty(),
                new StringBuilder("<if test=\"et instanceof java.util.Map\">")
                        .append("<if test=\"et.MP_OPTLOCK_VERSION_ORIGINAL!=null\">")
                        .append(" AND ${et.MP_OPTLOCK_VERSION_COLUMN}=#{et.MP_OPTLOCK_VERSION_ORIGINAL}")
                        .append("</if></if>"));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, "updateAllColumnById", sqlSource);
    }

    /**
     * <p>
     * SQL 更新 set 语句
     * </p>
     *
     * @param logic  是否逻辑删除注入器
     * @param table  表信息
     * @param prefix 前缀
     * @return sql
     */
    protected String sqlSet(boolean logic, TableInfo table, String prefix) {
        return SqlScriptUtils.convertSet(getAllSqlSet(table, logic, prefix));
    }

    /**
     * 获取所有的 sql set 片段
     *
     * @param ignoreLogicDelFiled 是否过滤掉逻辑删除字段
     * @param prefix              前缀
     * @return sql 脚本片段
     */
    public String getAllSqlSet(TableInfo table, boolean ignoreLogicDelFiled, final String prefix) {
        final String newPrefix = Objects.isNull(prefix) ? EMPTY : prefix;
        return table.getFieldList().stream()
                .filter(i -> !ignoreLogicDelFiled || !(table.isLogicDelete() && i.isLogicDelete())).map(i -> getSqlSet(i, newPrefix)).collect(joining(NEWLINE));
    }

    /**
     * 获取 set sql 片段
     *
     * @param prefix 前缀
     * @return sql 脚本片段
     */
    public String getSqlSet(TableFieldInfo tableFieldInfo, final String prefix) {
        final String newPrefix = prefix == null ? EMPTY : prefix;
        // 默认: column=
        String sqlSet = tableFieldInfo.getColumn() + EQUALS;
        if (StringUtils.isNotEmpty(tableFieldInfo.getUpdate())) {
            sqlSet += String.format(tableFieldInfo.getUpdate(), tableFieldInfo.getColumn());
        } else {
            sqlSet += SqlScriptUtils.safeParam(newPrefix + tableFieldInfo.getEl());
        }
        sqlSet += COMMA;
        return sqlSet;
    }
}
