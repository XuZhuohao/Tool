package com.yui.tool.imgremove.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * 图片对象
 *
 * @author XuZhuohao
 * @date 2018/11/12
 */
@Setter
@Getter
public class ImageDto {
    private String name;
    private String suffix;
    private String oldUrl;
    private String oldUrlFormat;
    private String path;
    private String newUrl;
    private File mdFile;
}
