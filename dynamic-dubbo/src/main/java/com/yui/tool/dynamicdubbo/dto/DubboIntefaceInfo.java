package com.yui.tool.dynamicdubbo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author XuZhuohao
 * @date 2020/1/3
 */
@Data
@Accessors(chain = true)
public class DubboIntefaceInfo {
    /**
     * TODO: result type
     * 接口名
     */
    private String name;
    /**
     * 版本号
     */
    private String version;
    /**
     * 方法信息
     */
    private List<MethodInfo> methodInfo;

}
