package com.yui.tool.monitor.zk.Watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * 子节点监视器
 *
 * @author XuZhuohao
 * @date 2019/7/19
 */
public class NodeChildrenChangedWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        if(Event.EventType.NodeChildrenChanged ==  event.getType()){
            System.err.println(event.getPath());
        }
    }
}
