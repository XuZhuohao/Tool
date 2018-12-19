package com.yui.tool.mybatis.plugin.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author XuZhuohao
 * @date 2018/12/14
 */
public class DateUtils {
    /**
     * 添加方法，时间转换
     *
     * @param resultType 转换的结果类型，对象需要拥有需要 valueOf(String) 方法
     * @param date       日期
     * @param format     格式化格式
     * @param <T>        结果泛型
     * @return 返回格式化后结果
     */
    public static <T> T getDateFormat(Class<T> resultType, Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            if (resultType.equals(String.class)){
                return (T) sf.format(date);
            }
            Method method = resultType.getMethod("valueOf", String.class);
            final Object result = method.invoke(null, sf.format(date));
            return (T) result;
        } catch (Exception e) {
            throw new RuntimeException("时间转换异常：" + e);
        }
    }
}
