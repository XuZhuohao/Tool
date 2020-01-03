package com.yui.tool.dynamicdubbo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XuZhuohao
 * @date 2020/1/3
 */
@Data
@Accessors(chain = true)
public class MethodInfo {
    /**
     * 唯一id
     */
    private String id;
    /**
     * 方法名
     */
    private String name;
    /**
     * 参数个数
     */
    private int parameterCount;
    /**
     * 参数类型集合
     */
    private String[] paramType;

    public MethodInfo() {
    }

    public MethodInfo(Method method) {
        this.name = method.getName();
        this.parameterCount = method.getParameterCount();
        this.paramType = new String[this.parameterCount];
        int i = 0;
        for (Class<?> parameterType : method.getParameterTypes()) {
            this.paramType[i] = parameterType.getName();
            i++;
        }
    }
}
