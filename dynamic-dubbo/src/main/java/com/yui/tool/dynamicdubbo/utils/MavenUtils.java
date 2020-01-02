package com.yui.tool.dynamicdubbo.utils;

import com.yui.tool.dynamicdubbo.dto.Dependency;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XuZhuohao
 * @date 2020/1/2
 */
public class MavenUtils {
    private final static Pattern ARTIFACT_ID_REG = Pattern.compile("<artifactId>([^<]+)</artifactId>");
    private final static Pattern GROUP_ID_REG = Pattern.compile("<groupId>([^<]+)</groupId>");
    private final static Pattern VERSION_REG = Pattern.compile("<version>([^<]+)</version>");

    private final static String VM_DIR = "vm/maven/download.xml.vm";
    /**
     * TODO 启动是检测环境变量
     */
    private final static String MAVEN_HOME = System.getProperty("M2_HOME");


    public static void main(String[] args) throws Exception {
        String pom = "<!-- apollo -->\n" +
                "        <dependency>\n" +
                "            <groupId>cn.com.bluemoon.apollo</groupId>\n" +
                "            <artifactId>apollo-client</artifactId>\n" +
                "            <version>0.0.1-SNAPSHOT</version>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>cn.com.bluemoon</groupId>\n" +
                "            <artifactId>short-url-api</artifactId>\n" +
                "            <version>1.0.0</version>\n" +
                "        </dependency>\n" +
                "        <!--<dependency>\n" +
                "            <groupId>cn.com.bluemoon</groupId>\n" +
                "            <artifactId>dubbo-starter</artifactId>\n" +
                "            <version>${dubbo.starter.vaersion}</version>\n" +
                "        </dependency>-->\n" +
                "        <dependency>\n" +
                "            <groupId>com.alibaba.boot</groupId>\n" +
                "            <artifactId>dubbo-spring-boot-starter</artifactId>\n" +
                "            <version>${dubbo.starter.version}</version>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>org.springframework.boot</groupId>\n" +
                "            <artifactId>spring-boot-starter-data-jpa</artifactId>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>org.springframework.boot</groupId>\n" +
                "            <artifactId>spring-boot-starter-data-redis</artifactId>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>org.springframework.boot</groupId>\n" +
                "            <artifactId>spring-boot-starter-web</artifactId>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>mysql</groupId>\n" +
                "            <artifactId>mysql-connector-java</artifactId>\n" +
                "            <version>5.1.39</version>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>org.springframework.boot</groupId>\n" +
                "            <artifactId>spring-boot-starter-test</artifactId>\n" +
                "            <scope>test</scope>\n" +
                "        </dependency>";
        downloadDependency(pom, "temp\\download1.xml", "D:\\software\\apache-maven-3.5.4\\conf\\bluemoon-settings.xml");
    }

    public static void downloadDependency(String dependency, String targetPath, String mavenSettingFile) throws Exception {
        // 解析文本，获取所有依赖对象
        final List<Dependency> dependencies = analysisDependencies(dependency);
        final HashMap<String, Object> imMap = new HashMap<>(16);
        imMap.put("dependencies", dependencies);
        // 构建 pom.xml 文件
        VelocityUtils.buildVm(VM_DIR, targetPath, imMap);
        final File pomFile = new File(targetPath);
        // maven 执行 mvn -s "D:\software\apache-maven-3.5.4\conf\bluemoon-settings.xml" -f download1.xml dependency:copy-dependencies
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(pomFile);
        request.setGoals(Arrays.asList("compile", "dependency:copy-dependencies"));

        if (mavenSettingFile != null) {
            final File userSettings = new File(mavenSettingFile);
            if (userSettings.exists()) {
                request.setUserSettingsFile(userSettings);
            }
        }
        request.setAlsoMakeDependents(true);
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("D:\\software\\apache-maven-3.5.4"));
        invoker.setWorkingDirectory(pomFile.getParentFile());
        invoker.execute(request);
    }

    private static List<Dependency> analysisDependencies(String dependency) {
        List<Dependency> dependencies = new ArrayList<>(16);
        final String[] split = dependency.split("</dependency>([\\s]+)<dependency>");
        for (String text : split) {
            String artifactId = getReg(text, ARTIFACT_ID_REG);
            String groupId = getReg(text, GROUP_ID_REG);
            String version = getReg(text, VERSION_REG);
            if (version == null || artifactId == null || groupId == null){
                continue;
            }
            final Dependency dependencyTemp = new Dependency().setVersion(version)
                    .setGroupId(groupId)
                    .setArtifactId(artifactId);
            dependencies.add(dependencyTemp);
        }
        return dependencies;
    }

    /*    private static List<String> getReg(String text, Pattern pattern) {
            List<String> result = new ArrayList<>(16);
            final Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                result.add(matcher.group(1));
            }
            return result;
        }*/
    private static String getReg(String text, Pattern pattern) {
        final Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
