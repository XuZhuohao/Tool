package com.yui.tool.github.service.impl;

import com.yui.tool.github.domain.ImageObject;
import com.yui.tool.github.service.ImageCollect;
import com.yui.tool.github.util.ImageCollectUtil;
import com.yui.tool.github.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图片收集 - bing
 *
 * @author XuZhuohao
 * @date 2018/10/16
 */
public class BingImageCollectImpl implements ImageCollect {

    private final static String MAIN_URL = "https://cn.bing.com";

    /**
     * TODO: BingImageCollectImpl是否可以修改成base类型，getImageObject 传入 patternStr， MAIN_URL，groupIndexes
     * @return
     */
    @Override
    public List<ImageObject> getImageObject() {
        String patternStr = "g_img\\=\\{url:\\s*\"([^\"]*)\"";
        // 获取图片地址数组
        List<String> urls = ImageCollectUtil.getUrlByPatternOfUrl(patternStr, MAIN_URL,1);
        List<ImageObject> imageObjectList = new ArrayList<>(16);
        // 解析bing网页获取相应的image url, name, suffix属性
        urls.forEach(url -> {
            ImageObject imageObject = new ImageObject();
            imageObject.setUrl(MAIN_URL + url);
            // 第一个 全称(index : 0)， 第二个 名字(index : 1)， 第三个 后缀 (index : 2)
            List<String> dealUrls = ImageCollectUtil.getUrlByPatternOfStr("(([^/|.]*)[\\.]{1}(.*))", url, 1,2,3);
            imageObject.setName(dealUrls.get(1));
            imageObject.setSuffix(dealUrls.get(2));
            imageObjectList.add(imageObject);
        });
        // TODO: 下载图片，交给通用类处理，是否要在解析的时候就赋予diskPath？增加imageObject成功下载标志
        return imageObjectList;
    }
}
