package com.yui.tool.monitor.web.utils;

import java.net.URL;
import java.util.Map;

/**
 * @author XuZhuohao
 * @date 2019/7/19
 */
public class StringUtils {

    public static boolean isCollectionsEmpty(Map c) {
        return c == null || c.size() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    public static boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    public static String splashToDot(String name) {
        return name.replaceAll("/", "\\.");
    }

    public static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    /**
     * "Apple.class" -> "Apple"
     */
    public static String trimExtension(String name) {
        int pos = name.lastIndexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }
}
