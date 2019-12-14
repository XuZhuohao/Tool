package com.yui.tool.monitor.web.service.impl;

import com.yui.tool.monitor.web.cache.HandlerCache;
import com.yui.tool.monitor.web.cache.ZkCache;
import com.yui.tool.monitor.web.service.ZkMonitorService;
import com.yui.tool.monitor.zk.service.WatcherHandler;
import com.yui.tool.monitor.zk.watcher.ZkService;
import org.apache.zookeeper.Watcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XuZhuohao
 * @date 2019/7/30
 */
@Service
public class ZkMonitorServiceImpl implements ZkMonitorService {
    @Override
    public List<ZkService> getAllZkServices() {
        return ZkCache.getAllService();
    }

    @Override
    public ZkService getZkService(String key) {
        return ZkCache.getService(key);
    }

    @Override
    public ZkService addZkService(String key, String address, int timeout) {
        return ZkCache.getZkService(key, address, timeout);
    }

    @Override
    public <T> boolean addZkWatcher(Watcher.Event.EventType eventType, String path, String serviceKey, String watcherHandlerKey, String initHandlerVo) {
        try {
            final WatcherHandler watcherHandler = HandlerCache.getWatcherHandler(watcherHandlerKey).newInstance();
            watcherHandler.init(initHandlerVo);
            this.getZkService(serviceKey).getZkWatcher(path).addWatcherHandler(eventType, watcherHandler);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
