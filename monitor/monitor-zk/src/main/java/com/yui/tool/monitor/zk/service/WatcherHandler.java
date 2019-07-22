package com.yui.tool.monitor.zk.service;

import com.yui.tool.monitor.zk.watcher.ZkService;
import org.apache.zookeeper.WatchedEvent;

/**
 * watcher 动作借口
 *
 * @author XuZhuohao
 * @date 2019/7/22
 */
public interface WatcherHandler {
    /**
     * 处理过程
     * @param event watch 处理时间
     * @return 处理结果
     */
    boolean process(WatchedEvent event, ZkService zkService);
}
