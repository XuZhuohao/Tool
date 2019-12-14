package com.yui.tool.monitor.web.service;

import com.yui.tool.monitor.zk.service.WatcherHandler;
import com.yui.tool.monitor.zk.watcher.ZkService;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * @author XuZhuohao
 * @date 2019/7/23
 */
public interface ZkMonitorService {
    /**
     * 获取所有 zk 服务
     * @return zk list
     */
    List<ZkService> getAllZkServices();

    /**
     * 获取一个 zk 服务
     * @param key zk key
     * @return zk
     */
    ZkService getZkService(String key);

    /**
     * 添加一个 zk 服务
     * @param key zk key
     * @param address address
     * @param timeout timeout
     * @return zk 服务
     */
    ZkService addZkService(String key, String address, int timeout);

    /**
     * 为服务添加一个处理
     * @param eventType {@link Watcher.Event.EventType} 事件类型
     * @param path 监控路径
     * @param serviceKey 服务 key
     * @param watcherHandlerKey 观察者操作 key
     * @param initHandlerVo  初始化参数
     * @return 是否成功
     */
    <T> boolean addZkWatcher(Watcher.Event.EventType eventType, String path, String serviceKey, String watcherHandlerKey, String initHandlerVo);

}
