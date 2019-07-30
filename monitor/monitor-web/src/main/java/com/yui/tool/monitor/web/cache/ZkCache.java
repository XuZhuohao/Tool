package com.yui.tool.monitor.web.cache;

import com.yui.tool.monitor.zk.service.impl.DefaultZkHandler;
import com.yui.tool.monitor.zk.watcher.ZkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author XuZhuohao
 * @date 2019/7/25
 */
public class ZkCache {
    private static Map<String, ZkService> ZK_SERVICE_CACHE = new HashMap<>(16);

    public static ZkService getZkService(String key, String address, int timeout){
        ZkService zkService = ZK_SERVICE_CACHE.get(key);
        if (zkService != null){
            return zkService;
        }
        zkService = ZkService.getInstance(address, timeout, new DefaultZkHandler());
        ZK_SERVICE_CACHE.put(key, zkService);
        return zkService;
    }

    public static List<ZkService> getAllService(){
        return new ArrayList<>(ZK_SERVICE_CACHE.values());
    }

    public static ZkService getService(String key){
        return ZK_SERVICE_CACHE.get(key);
    }

}
