package com.yui.tool.monitor.web.handler;

import java.lang.annotation.*;

/**
 * @author XuZhuohao
 * @date 2019/7/30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WathcerHandler {
    /**
     * 名称
     */
    String name();

    /**
     * 简介
     */
    String desc();

    /**
     * 初始化构造
     */
    Class initClass();
}
