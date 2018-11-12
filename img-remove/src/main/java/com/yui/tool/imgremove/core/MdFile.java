package com.yui.tool.imgremove.core;

import com.yui.tool.imgremove.dto.ImageDto;

import java.io.File;
import java.util.List;

/**
 * markdown文件解析
 *
 * @author XuZhuohao
 * @date 2018/11/12
 */
public interface MdFile {
    /**
     * 获取image处理对象
     * @param path 文件夹路径
     * @return ImageDto结果集
     */
    List<ImageDto> getImagesFromMdFile(String path);

    /**
     * 获取所有md文件对象
     * @param path 路径
     * @return md文件对象结果集
     */
    List<File> getAllMdFile(String path);
}
