package com.yui.tool.monitor.zk.watcher;

import com.yui.tool.monitor.zk.service.WatcherHandler;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author XuZhuohao
 * @date 2019/7/22
 */

public class ZkWatcher implements Watcher {
    public static Map<String, ZkWatcher> allWatcher = new HashMap<>(16);
    /**
     * 所有
     */
    private Map<Event.EventType, Set<WatcherHandler>> watcherHandlerMap = new HashMap<>(16);
    private ZkService zkService;

    private ZkWatcher(String path,ZkService zkService) {
        this.zkService = zkService;
        allWatcher.put(path, this);
    }

    /**
     * TODO: 多个节点共用一个实例是否会有问题
     * 获取 watcher 实例
     *
     * @param path
     * @return
     */
    public synchronized static ZkWatcher getInstance(String path, ZkService zkService) {
        // 从缓存里面拿取
        ZkWatcher zkWatcher = allWatcher.get(path + zkService.getAddress());
        if (zkWatcher != null) {
            return zkWatcher;
        }
        // 创建一个实例
        zkWatcher = new ZkWatcher(path,zkService);
        allWatcher.put(path  + zkService.getAddress(), zkWatcher);
        return zkWatcher;
    }


    @Override
    public void process(WatchedEvent event) {
        Set<WatcherHandler> watcherHandlers = watcherHandlerMap.get(event.getType());
        watcherHandlers.forEach(watcherHandler -> {
            final boolean process = watcherHandler.process(event, this.zkService);
            // 返回结果为 true 且非删除节点事重新添加 watcher
            System.out.println(process);
            if (event.getType() != Event.EventType.NodeDeleted && process) {
                System.out.println("add agagin:" + watcherHandler.getClass() + "  \t" + event.getPath());
                this.zkService.addWatch(event.getPath(), this);
            }

        });
    }

    /**
     * 添加 watcherHandler
     * @param eventType eventType
     * @param watcherHandler watcherHandler
     */
    public void addWatcherHandler(Event.EventType eventType, WatcherHandler watcherHandler) {
        Set<WatcherHandler> watcherHandlers = watcherHandlerMap.get(eventType);
        if (watcherHandlers == null){
            watcherHandlers = new HashSet<>(16);
        }
        watcherHandlers.add(watcherHandler);
        watcherHandlerMap.put(eventType, watcherHandlers);
    }
}
