package com.yui.tool.dynamicdubbo.cache;

import com.yui.tool.dynamicdubbo.dto.DubboIntefaceInfo;
import com.yui.tool.dynamicdubbo.dto.MethodInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XuZhuohao
 * @date 2020/1/3
 */
@Data
@Accessors(chain = true)
public class DubboServiceCache {
    /**
     * 主存
     */
    private volatile static Map<Integer, DubboIntefaceInfo> CACHE_MAIN = new HashMap<>(16);
    /**
     * 子存
     */
    private volatile static Map<Integer, MethodInfo> CACHE_METHOD = new HashMap<>(16);

    /**
     * 依赖添加，主存增到子存
     *
     * @param dubboIntefaceInfo dubboIntefaceInfo
     * @return 链式
     */
    public static Map<Integer, DubboIntefaceInfo> addDubboInteface(DubboIntefaceInfo dubboIntefaceInfo) {
        CACHE_MAIN.put(dubboIntefaceInfo.getId(), dubboIntefaceInfo);
        for (MethodInfo methodInfo : dubboIntefaceInfo.getMethodInfo()) {
            CACHE_METHOD.put(methodInfo.getId(), methodInfo);
        }
        return CACHE_MAIN;
    }

    public static Map<Integer, DubboIntefaceInfo> getCacheMain() {
        return CACHE_MAIN;
    }

    public static Map<Integer, MethodInfo> getCacheMethod() {
        return CACHE_METHOD;
    }

}
