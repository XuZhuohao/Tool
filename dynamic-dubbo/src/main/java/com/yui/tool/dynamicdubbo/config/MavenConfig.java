package com.yui.tool.dynamicdubbo.config;

import com.yui.tool.dynamicdubbo.utils.MavenUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author XuZhuohao
 * @date 2020-03-04 14:58
 */
@Data
@Accessors(chain = true)
@Configuration
@ConfigurationProperties(prefix = "dynamic.dubbo.maven")
public class MavenConfig {
    /**
     * MAVEN_HOME
     */
    private String home;
    /**
     * 下载地址
     */
    private String downloadPath;
    /**
     * pom 生成文件位置
     */
    private String pomPath;
    /**
     * setting file
     */
    private String settingFile;

    public void setHome(String home) {
        this.home = home;
        MavenUtils.setMavenHome(this.home);
    }
}
