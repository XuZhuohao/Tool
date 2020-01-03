package com.yui.tool.dynamicdubbo.service;

import com.yui.tool.dynamicdubbo.dto.DubboIntefaceInfo;
import com.yui.tool.dynamicdubbo.dto.ResultBean;

import java.util.List;

/**
 * @author XuZhuohao
 * @date 2020/1/3
 */
public interface DubboService {
    /**
     * 添加 dubbo api 依赖
     * @param dependency 依赖
     * @return 分析列表
     */
    ResultBean<List<DubboIntefaceInfo>> addDubboApi(String dependency);
}
