package com.yui.tool.dynamicdubbo.service;

import com.yui.tool.dynamicdubbo.dto.DubboIntefaceInfo;
import com.yui.tool.dynamicdubbo.dto.ResultBean;

import java.util.List;
import java.util.Map;

/**
 * @author XuZhuohao
 * @date 2020/1/3
 */
public interface DubboService {
    /**
     * 添加 dubbo api 依赖
     *
     * @param dependency 依赖
     * @return 分析列表
     */
    ResultBean<List<DubboIntefaceInfo>> addDubboApi(String dependency);

    /**
     * 调用 dubbo，如果改方法存在重载方法，且参数数量一直，可能会出现调用异常
     *
     * @param interfaceName 接口名
     * @param version 版本好
     * @param inMap 入参
     * @return 调用结果
     */
    ResultBean<String> invoke(String interfaceName, String method, String version, Map<String, Object> inMap);

    /**
     * 根据 id 调用 dubbo 服务
     * @param id id 添加时返回
     * @param version 版本号
     * @param inMap 入参
     * @return 结果
     */
    ResultBean<String> invoke(String id, String version, Map<String, Object> inMap);
}
