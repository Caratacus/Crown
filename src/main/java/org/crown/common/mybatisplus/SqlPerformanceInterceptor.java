package org.crown.common.mybatisplus;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.mysql.cj.jdbc.ServerPreparedStatement;


/**
 * <p>
 * SQL性能分析拦截器
 * </p>
 *
 * @author Caratacus
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
public class SqlPerformanceInterceptor implements Interceptor {

    private static final Log logger = LogFactory.getLog(SqlPerformanceInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception e) {
            // do nothing
        }
        if (stmtMetaObj.hasGetter("delegate")) {//Hikari
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception ignored) {

            }
        }

        String originalSql = ((ServerPreparedStatement) statement).asSql(false);
        originalSql = originalSql.replaceAll("[\\s]+", " ");
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }
        StringBuilder formatSql = new StringBuilder();
        formatSql.append(" Execute SQL：").append(SqlUtils.sqlFormat(originalSql, false));
        logger.debug(formatSql.toString());
        // 计算执行 SQL 耗时
        long start = SystemClock.now();
        Object result = invocation.proceed();
        long timing = SystemClock.now() - start;
        // 格式化 SQL 打印执行结果
        Object target = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        StringBuilder msStr = new StringBuilder();
        msStr.append(" Time：").append(timing);
        msStr.append(" ms - ID：").append(ms.getId());
        logger.debug(msStr.toString());
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    @SuppressWarnings("EmptyMethod")
    public void setProperties(Properties properties) {

    }

    public Method getMethodRegular(Class<?> clazz, String methodName) {
        if (Object.class.equals(clazz)) {
            return null;
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return getMethodRegular(clazz.getSuperclass(), methodName);
    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql
     * @return
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0);
    }
}
