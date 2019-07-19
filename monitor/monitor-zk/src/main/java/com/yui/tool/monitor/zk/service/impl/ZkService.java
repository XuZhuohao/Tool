package com.yui.tool.monitor.zk.service.impl;

import com.alibaba.fastjson.JSON;
import com.yui.tool.monitor.zk.service.ZkHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * zkService
 *
 * @author XuZhuohao
 * @date 2019/7/19
 */
@Data
@Slf4j
public class ZkService implements Watcher {
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
    private ZooKeeper zooKeeper;
    private String address;
    private int timeout = 5000;
    private ZkHandler zkHandler;
    private Map<String, List<Watcher>> pathWatcher= new HashMap<>(16);

    /**
     * 封闭构造函数
     */
    private ZkService() {
    }

    public static ZkService getInstance(String address, int timeout, ZkHandler zkHandler) {
        final ZkService zkService = new ZkService();
        zkService.setZkHandler(zkHandler);
        zkService.init(address, timeout);
        return zkService;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("receive the event:" + event);
        if (Event.KeeperState.SyncConnected == event.getState() || Event.EventType.None == event.getType()) {
            COUNT_DOWN_LATCH.countDown();
            this.zkHandler.established(event, this);
        }
        if(Event.EventType.NodeChildrenChanged ==  event.getType()){
            this.zkHandler.nodeChildrenChanged(event, this);
        }
    }

    private void init(String address, int timeout) {
        try {
            this.address = address;
            this.timeout = timeout;
            zooKeeper = new ZooKeeper(address, timeout, this);
            COUNT_DOWN_LATCH.await();
            log.info("zookeeper connection state:%s", zooKeeper.getState());
        } catch (Exception e) {
            log.error("zookeeper connection exception:%s", zooKeeper.getState());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param path
     * @param value
     */
    public void createEphemeral(String path, String value){
        try {
            this.zooKeeper.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加 watch
     * @param path 节点
     * @param watcher watcher
     */
    public void addWatch(String path, Watcher watcher){
        List<Watcher> watchers = pathWatcher.get(path);
        if (watchers == null){
            watchers = new ArrayList<>();
            watchers.add(watcher);
            pathWatcher.put(path, watchers);
        }
        try {
            this.zooKeeper.getData(path, watcher, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
