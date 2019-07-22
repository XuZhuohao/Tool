package com.yui.tool.monitor.zk.watcher;

import com.yui.tool.monitor.zk.service.WatcherHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.*;

/**
 * @author XuZhuohao
 * @date 2019/7/22
 */
@Data
@Slf4j
public class ZkWatcher implements Watcher {
    private static Map<String, ZkWatcher> allWatcher = new HashMap<>(16);
    /**
     * 所有
     */
    private Map<Event.EventType, Set<WatcherHandler>> watcherHandlerMap = new HashMap<>(16);
    private ZkService zkService;
    private String path;

    private ZkWatcher(String path, ZkService zkService) {
        this.zkService = zkService;
        this.path = path;
    }

    /**
     * 获取 watcher 实例
     *
     * @param path 路径
     * @param zkService zkService 实例
     * @return ZkWatcher
     */
    public synchronized static ZkWatcher getInstance(String path, ZkService zkService) {
        // 从缓存里面拿取
        ZkWatcher zkWatcher = allWatcher.get(path + zkService.getAddress());
        if (zkWatcher != null) {
            return zkWatcher;
        }
        // 创建一个实例
        zkWatcher = new ZkWatcher(path, zkService);
        allWatcher.put(path + zkService.getAddress(), zkWatcher);
        return zkWatcher;
    }

    @Override
    public void process(WatchedEvent event) {
        Set<WatcherHandler> watcherHandlers = watcherHandlerMap.get(event.getType());
        // 业务处理入口
        watcherHandlers.forEach(watcherHandler -> {
            final boolean process = watcherHandler.process(event, this.zkService);
            // 返回结果为 false 则移除该处理
            if (!process){
                watcherHandlers.remove(watcherHandler);
            }
        });
        // 非删除节点事重新添加 watcher
        if (event.getType() != Event.EventType.NodeDeleted) {
            this.zkService.addWatch(event.getPath(), this);
        }
    }

    /**
     * 添加 watcherHandler
     *
     * @param eventType      {@link Event.EventType} 时间类型
     * @param watcherHandler watcherHandler 处理实现
     */
    public void addWatcherHandler(Event.EventType eventType, WatcherHandler watcherHandler) {
        Set<WatcherHandler> watcherHandlers = watcherHandlerMap.get(eventType);
        if (watcherHandlers == null) {
            watcherHandlers = new HashSet<>(16);
        }
        watcherHandlers.add(watcherHandler);
        watcherHandlerMap.put(eventType, watcherHandlers);
        this.zkService.addWatch(this.path, this);
    }
}
