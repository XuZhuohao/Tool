package com.yui.tool.dynamicdubbo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * maven 毅力数据对象
 * @author XuZhuohao
 * @date 2020/1/2
 */
@Data
@Accessors(chain = true)
public class Dependency {
    private String groupId;
    private String artifactId;
    private String version;
    private String scope;
    private String classifier;
}
