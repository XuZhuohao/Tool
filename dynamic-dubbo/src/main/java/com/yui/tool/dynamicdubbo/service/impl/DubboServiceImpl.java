package com.yui.tool.dynamicdubbo.service.impl;

import com.yui.tool.dynamicdubbo.dto.DubboIntefaceInfo;
import com.yui.tool.dynamicdubbo.dto.ResultBean;
import com.yui.tool.dynamicdubbo.service.DubboService;

import java.util.List;
import java.util.Map;

/**
 * @author XuZhuohao
 * @date 2020/1/3
 */
public class DubboServiceImpl implements DubboService {
    @Override
    public ResultBean<List<DubboIntefaceInfo>> addDubboApi(String dependency) {
        return null;
    }

    @Override
    public ResultBean<String> invoke(String interfaceName, String method, String version, Map<String, Object> inMap) {
        return null;
    }

    @Override
    public ResultBean<String> invoke(String id, String version, Map<String, Object> inMap) {
        return null;
    }
}
