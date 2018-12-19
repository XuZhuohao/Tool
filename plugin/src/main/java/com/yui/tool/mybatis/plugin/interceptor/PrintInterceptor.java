package com.yui.tool.mybatis.plugin.interceptor;

import com.yui.tool.mybatis.plugin.util.DateUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

/**
 * 插入拦截
 *
 * @author XuZhuohao
 * @date 2018/12/14
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class,
        method = "prepare", args = {Connection.class, Integer.class})})
public class PrintInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        final MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        SqlCommandType sqlCmdType = ms.getSqlCommandType();
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 入参
        Object parameterObject = boundSql.getParameterObject();
        //获取原始sql
        String originalSql = boundSql.getSql().replaceAll("\\s+", " ");
        logger.debug("==> originalSql:" + originalSql);
        logger.debug("==> parameterObject" + parameterObject.getClass());
        originalSql = getSqlOfType(parameterObject, originalSql);
        logger.debug("==> afterSql:" + originalSql);
        return invocation.proceed();
    }

    /**
     * 判断类别
     *
     * @param parameterObject 参数对象
     * @param originalSql sql 语句
     * @return sql 语句
     */
    private String getSqlOfType(Object parameterObject, String originalSql) throws Exception {
        // 拼接属性
        final Class<?> parameterObjectClass = parameterObject.getClass();
        // Char.class
        if (this.inEquals(parameterObjectClass, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class)) {
            originalSql = originalSql.replaceFirst("\\?", String.valueOf(parameterObject));
        } else if (parameterObjectClass.equals(Boolean.class)) {
            originalSql = originalSql.replaceFirst("\\?", ((boolean) parameterObject) ? "1" : "0");
        } else if (parameterObjectClass.equals(String.class)) {
            originalSql = originalSql.replaceFirst("\\?", "'" + String.valueOf(parameterObject) + "'");
        } else if (parameterObjectClass.equals(Date.class)) {
            originalSql = originalSql.replaceFirst("\\?", "'" +
                    DateUtils.getDateFormat(String.class, (Date) parameterObject, "yyyyMMddHHmmss") + "'");
        } else if (parameterObjectClass.equals(Map.class)) {
            originalSql = getSqlOfMap(originalSql, (Map) parameterObject);
        } else {
            originalSql = getSqlOfObj(originalSql, parameterObject);
        }
        return originalSql;
    }

    /**
     * 从对象拼接 sql
     * @param originalSql sql
     * @param obj obj
     * @return sql
     * @throws Exception IllegalAccessException
     */
    private String getSqlOfObj(String originalSql, Object obj) throws Exception {
        for (Field declaredField : obj.getClass().getDeclaredFields()) {
            //打开私有访问
            declaredField.setAccessible(true);
            //获取属性值
            Object value = declaredField.get(obj);
            boolean flag = originalSql.contains("insert") || originalSql.contains("update");
            if (value == null &&  flag ){
                originalSql = originalSql.replaceFirst("\\?", "''");
            } else if (value != null){
                originalSql = getSqlOfType(value, originalSql);
            }
        }
        return originalSql;
    }
    /**
     * 遍历 map
     *
     * @param originalSql sql
     * @param map         map
     * @return sql
     */
    private String getSqlOfMap(String originalSql, Map map) throws Exception {
        for (Object value : map.values()) {
            originalSql = getSqlOfType(value, originalSql);
        }
        return originalSql;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        String dialect = properties.getProperty("dialect");
        logger.info("mybatis intercept dialect:{}", dialect);
    }


    /**
     * in 判断
     *
     * @param source  判断对象
     * @param objects 范围
     * @return 是否存在
     */
    private boolean inEquals(Object source, Object... objects) {
        for (Object object : objects) {
            if (source.equals(object)) {
                return true;
            }
        }
        return false;
    }

}
