package com.yui.tool.dynamicdubbo.utils;

import com.alibaba.fastjson.JSON;
import com.yui.tool.dynamicdubbo.dto.Dependency;
import com.yui.tool.dynamicdubbo.dto.DubboIntefaceInfo;
import com.yui.tool.dynamicdubbo.dto.MethodInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author XuZhuohao
 * @date 2020/1/3
 */
public class JarUtils {
    private URLClassLoader classLoader;

    public static void main(String[] args) throws Exception {

        String text = "<dependency>\n" +
                "            <groupId>cn.com.bluemoon</groupId>\n" +
                "            <artifactId>short-message-api</artifactId>\n" +
                "            <version>0.0.1-SHNATSHOP</version>\n" +
                "        </dependency>";
        final List<Dependency> dependencies = MavenUtils.analysisDependencies(text);
        MavenUtils.downloadDependency(dependencies, "D:\\Projects\\tool\\dynamic-dubbo\\temp\\target\\t1\\download.xml", "D:\\software\\apache-maven-3.5.4\\conf\\bluemoon-settings.xml");

        final File file = new File("D:\\Projects\\tool\\dynamic-dubbo\\temp\\target\\t1\\target\\dependency");
        final JarUtils jarUtils = new JarUtils();
        final File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        List<DubboIntefaceInfo> dubboIntefaceInfos = new ArrayList<>(16);
        jarUtils.loadJars(Arrays.asList(files));
        for (File jarFile : files) {
            for (Dependency dependency : dependencies) {
                if (jarFile.getName().startsWith(dependency.getArtifactId())) {
                    dubboIntefaceInfos.addAll(jarUtils.getDubboInterfaceInfo(jarFile));
                }
            }
        }
        System.out.println(JSON.toJSONString(dubboIntefaceInfos));

    }

    /**
     * 加载 jar
     *
     * @param fileList 文件列表
     */
    public void loadJars(List<File> fileList) {
        final URL[] as = fileList.stream()
                .filter(file -> file.getName().endsWith(".jar"))
                .map(file -> {
                    try {
                        return file.toURI().toURL();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).toArray(URL[]::new);
        System.out.println(JSON.toJSONString(as));
        if (as.length == 0) {
            return;
        }
        classLoader = new URLClassLoader(as);
    }

    public List<DubboIntefaceInfo> getDubboInterfaceInfo(File file) throws Exception {
        final List<String> classes = analysisJar(file);
        List<DubboIntefaceInfo> result = new ArrayList<>(16);
        for (String clazzName : classes) {
            final Class<?> clazz = classLoader.loadClass(clazzName);
            if (!clazz.isInterface()) {
                continue;
            }
            final DubboIntefaceInfo dubboIntefaceInfo = new DubboIntefaceInfo();
            List<MethodInfo> methodInfo = new ArrayList<>(16);
            dubboIntefaceInfo.setName(clazzName);
            dubboIntefaceInfo.setMethodInfo(methodInfo);
            for (Method method : clazz.getMethods()) {
                methodInfo.add(new MethodInfo(method));
            }
            result.add(dubboIntefaceInfo);
        }
        return result;
    }

    public static List<String> analysisJar(File file) throws IOException {
        List<String> result = new ArrayList<>(16);
        final JarFile jarFile = new JarFile(file);
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry jarEntry = entries.nextElement();
            if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                continue;
            }
            result.add(jarEntry.getName().replaceAll("/", ".").replace(".class", ""));
        }
        return result;
    }
}
