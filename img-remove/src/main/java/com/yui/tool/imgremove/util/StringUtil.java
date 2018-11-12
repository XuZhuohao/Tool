package com.yui.tool.imgremove.util;

/**
 * 字符串工具类
 *
 * @author XuZhuohao
 * @date 2018/11/5
 */
public class StringUtil {
    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 如果为空返回true，否则为false
     */
    public static boolean isNull(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() == 0) {
            return true;
        }
        return false;
    }
    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 如果为空返回false，否则为true
     */
    public static boolean isNotNull(String str) {
        return !isNull(str);
    }
}
