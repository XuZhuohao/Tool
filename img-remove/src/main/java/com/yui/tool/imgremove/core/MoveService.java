package com.yui.tool.imgremove.core;

import com.yui.tool.imgremove.dto.ImageDto;

import java.util.List;

/**
 * 迁移服务
 *
 * @author XuZhuohao
 * @date 2018/11/12
 */
public interface MoveService {
    /**
     * 迁移
     * @param images 图片集合
     */
    void move(List<ImageDto> images);

}
