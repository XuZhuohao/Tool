package com.yui.tool.dynamicdubbo.utils;

import com.yui.tool.dynamicdubbo.dto.Dependency;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XuZhuohao
 * @date 2020/1/2
 */
public class VelocityUtils {
    private static VelocityEngine engine;
    static{
        engine = new VelocityEngine();
        // 设置入参
        engine.setProperty(org.apache.velocity.runtime.RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        // 初始化
        engine.init();
    }

    public static void buildVm(String vmFilePath, String targetFilePath, Map<String, Object> property) throws Exception {
        // 导入模版
        final Template template = engine.getTemplate(vmFilePath);
        VelocityContext ctx = new VelocityContext();
        // 放置参数
        property.forEach(ctx::put);
        try(FileWriter fileWriter = new FileWriter(new File(targetFilePath))) {
            template.merge(ctx, fileWriter);
        }
    }

    public static void main(String[] args) throws Exception {
        List<Dependency> dependencies = new ArrayList<>(16);
        Dependency dependency = new Dependency();
        dependency.setArtifactId("short-message-api")
                .setGroupId("cn.com.bluemoon")
                .setVersion("1.0.1.RELEASE");
        dependencies.add(dependency);

        final HashMap<String, Object> imMap = new HashMap<>(16);
        imMap.put("dependencies", dependencies);
        buildVm("vm/maven/download.xml.vm", "temp/download1.xml", imMap);
    }
}
