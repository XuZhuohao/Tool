package com.yui.tool.mybatis.plugin.annotation;

import com.yui.tool.mybatis.plugin.Runtime.AbstractCreateBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * create by
 *
 * @author XuZhuohao
 * @date 2018/12/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CreateBy {
    Class<? extends AbstractCreateBy> target();
}
