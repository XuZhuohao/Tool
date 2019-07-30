package com.yui.tool.monitor.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author XuZhuohao
 * @date 2019/7/30
 */
@Setter
@Getter
@Accessors(chain = true)
public class WatcherHandlerInfoDto {
    /**
     * key
     */
    private String key;
    /**
     * 名称
     */
    private String name;
    /**
     * 介绍
     */
    private String desc;
}
