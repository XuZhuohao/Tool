package com.yui.tool.github.config;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * 图片迁移配置文件
 *
 * @author XuZhuohao
 * @date 2018/10/15
 */
@Setter
@Getter
public class PicMoveParam {
    /**
     * 图片临时路径
     */
    private final static String PIC_TEMP_PATH = System.getProperty("user.dir") + File.separator + "target" + File.separator + "tempPic";
}
