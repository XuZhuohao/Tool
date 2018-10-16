package com.yui.tool.github.service;

import com.yui.tool.github.domain.ImageObject;

import java.util.List;

/**
 * 图片收集
 *
 * @author XuZhuohao
 * @date 2018/10/16
 */
public interface ImageCollect {
    /**
     * 获取图片对象集合 {@link com.yui.tool.github.domain.ImageObject}
     * @return 图片对象集合
     */
    List<ImageObject> getImageObject();
}
