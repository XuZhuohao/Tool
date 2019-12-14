package com.yui.tool.monitor.web.utils;


import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import static com.yui.tool.monitor.web.utils.StringUtils.*;

/**
 *
 * @author XuZhuohao
 * @date 2019/7/19
 *  */
public class PackageScanner {

    private boolean isUberJar = false;

    private ClassLoader classLoader;

    public PackageScanner() {
        classLoader = getClass().getClassLoader();
    }

    public List<String> doScan(String basePackage) throws IOException {
        return doScan(basePackage, new ArrayList<>());
    }

    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {

        String splashPath = dotToSplash(basePackage);

        URL url = classLoader.getResource(splashPath);

        Assert.notNull(url, "请检查你的扫描包配置路径是否存在");

        String filePath = getRootPath(url);

        List<String> names;

        if (isJarFile(filePath)) {
            names = readFromJarFile(filePath, splashPath);
        } else {
            names = readFromDirectory(filePath);
        }

        if (names != null) {
            for (String name : names) {
                if (isClassFile(name)) {
                    nameList.add(name.startsWith(basePackage) ? trimExtension(name)
                            : toFullyQualifiedName(name, basePackage));
                } else {
                    doScan(basePackage + "." + name, nameList);
                }
            }
        }

        return nameList;

    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {

        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList<>();
        while (null != entry) {
            String name = entry.getName();
            /*
             * 判断是否是 spring boot 项目
             */
            if (name.contains("BOOT-INF")) {
                nameList.clear();
                isUberJar = true;
                break;
            }
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(splashToDot(name));
            }
            entry = jarIn.getNextJarEntry();
        }

        /*
         * spring boot Uber jar
         */
        while (isUberJar && null != entry) {
            String name = entry.getName();
            if (name.startsWith("BOOT-INF/classes/" + splashedPackageName) && isClassFile(name)) {
                nameList.add(splashToDot(name.substring(name.indexOf(splashedPackageName))));
            }
            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    private List<String> readFromDirectory(String path) {

        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    /**
     * Convert short class name to fully qualified name. e.g., String ->
     * java.lang.String
     */
    private String toFullyQualifiedName(String shortName, String basePackage) {
        return basePackage + '.' +
                trimExtension(shortName);
    }

}
