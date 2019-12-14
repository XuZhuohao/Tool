package com.yui.tool.monitor.zk.service.impl;

import com.yui.tool.monitor.zk.service.WatcherHandler;
import com.yui.tool.monitor.zk.watcher.ZkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author XuZhuohao
 * @date 2019/7/22
 */
@Slf4j
public class ExampleNodeDataChangedWatcherHandlerImpl implements WatcherHandler {
    private String oldData;
    private String path;
    public ExampleNodeDataChangedWatcherHandlerImpl(String path, ZkService zkService){
        try {
            final byte[] data = zkService.getZooKeeper().getData(path, false, null);
            this.oldData = new String(data);
            this.path = path;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean process(WatchedEvent event, ZkService zkService) {
        if (event.getType() != Watcher.Event.EventType.NodeDataChanged){
            throw new RuntimeException("错误！！");
        }
        try {
            final byte[] data = zkService.getZooKeeper().getData(this.path, false, null);
            String newData = new String(data);
            if (!oldData.equals(newData)){
                System.err.println(String.format("节点：%s, 发生了变化，原值为：%s, 新值为:%s， 停止监听。。。", this.path, this.oldData, newData));
                this.oldData = newData;
                // 停止监听
                return false;
            }
            System.err.println(String.format("节点：%s, 没有变化，原值为：%s, 新值为:%s， 继续监听。。。", this.path, this.oldData, newData));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
