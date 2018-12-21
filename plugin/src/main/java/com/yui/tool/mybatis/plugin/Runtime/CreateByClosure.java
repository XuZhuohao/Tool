package com.yui.tool.mybatis.plugin.Runtime;

import com.yui.tool.mybatis.plugin.annotation.CreateBy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author XuZhuohao
 * @date 2018/12/21
 */
public class CreateByClosure {
    /**
     * 对注解对象添加属性
     * @param o
     */
    public static void addUser(Object o){
        for (Field declaredField : o.getClass().getDeclaredFields()) {
            if (!declaredField.isAnnotationPresent(CreateBy.class)) {
                continue;
            }
            final CreateBy annotation = declaredField.getAnnotation(CreateBy.class);
            try {
                final AbstractCreateBy createByOf = annotation.target().getConstructor().newInstance();
                Object obj = createByOf.getUser();
                // TODO:类型不一致
                if(declaredField.getType() != obj.getClass() ){
                    System.err.println("类型不一致");
                }
                addValueToProperty(declaredField.getName(), o, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void addValueToProperty(String propertyName, Object object, Object value){
        // 首字母大写
        propertyName = propertyName.toUpperCase().substring(0,1) + propertyName.substring(1);
        try {
            // 获取 get 方法
            Method getMethod = object.getClass().getMethod("get" + propertyName);
            // 获取 set 方法
            Method setMethod = object.getClass().getMethod("set" + propertyName, value.getClass());
            // 如果 get 不到值， 将 value set 进去
            if (getMethod.invoke(object) == null){
                setMethod.invoke(object, value);
            }
        } catch (Exception e) {
            // 异常时处理位
            // 可能发生异常：
            //      1.找不到对应的 set / get 方法；
            //      2.对应的 set 方法，入参类型与 value 类型不符合
            //      3.没有对应的属性
            e.printStackTrace();
        }
    }
}
