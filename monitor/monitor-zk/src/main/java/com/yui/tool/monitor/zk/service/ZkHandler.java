package com.yui.tool.monitor.zk.service;

import com.yui.tool.monitor.zk.service.impl.ZkService;
import org.apache.zookeeper.WatchedEvent;

/**
 * @author XuZhuohao
 * @date 2019/7/19
 */
public interface ZkHandler {
    /**
     * 建立连接
     */
    void established(WatchedEvent event, ZkService zkService);

    void nodeChildrenChanged(WatchedEvent event, ZkService zkService);
}
