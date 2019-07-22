package com.yui.tool.monitor.zk.service;

import com.yui.tool.monitor.zk.service.impl.DefaultZkHandler;
import com.yui.tool.monitor.zk.service.impl.ExampleNodeDataChangedWathcerHandlerImpl;
import com.yui.tool.monitor.zk.watcher.ZkService;
import com.yui.tool.monitor.zk.watcher.ZkWatcher;
import org.apache.zookeeper.Watcher;
import org.junit.Test;

/**
 * @author XuZhuohao
 * @date 2019/7/22
 */
public class ZkServiceTest {
    @Test
    public void allTest() throws Exception {
        final ZkService zkServiceImpl = ZkService.getInstance("127.0.0.1:2181", 5000, new DefaultZkHandler());
        zkServiceImpl.createEphemeral("/test01", "t01");
        zkServiceImpl.createEphemeral("/test02", "t02");
        //添加监控节点 /test01，监控事件为 NodeDataChanged，NodeDeleted
        final ZkWatcher instance = ZkWatcher.getInstance("/test01", zkServiceImpl);
        instance.addWatcherHandler(Watcher.Event.EventType.NodeDataChanged,
                (event, zkService) -> {
                    try {
                        System.out.println("NodeDataChanged:" + event.getPath()
                                + ", new Data:" + new String(zkService.getZooKeeper().getData(event.getPath(), false, null)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
        instance.addWatcherHandler(Watcher.Event.EventType.NodeDeleted,
                (event, zkService) -> {
                    System.out.println("NodeDeleted:" + event.getPath());
                    return true;
                });
        instance.addWatcherHandler(Watcher.Event.EventType.NodeDataChanged, new ExampleNodeDataChangedWathcerHandlerImpl("/test01", zkServiceImpl));
        zkServiceImpl.addWatch("/test01", instance);

        //添加监控节点 /test02，监控事件为 NodeDataChanged，NodeDeleted
        final ZkWatcher instance1 = ZkWatcher.getInstance("/test02", zkServiceImpl);
        instance1.addWatcherHandler(Watcher.Event.EventType.NodeDataChanged,
                (event, zkService) -> {
                    try {
                        System.out.println("NodeDataChanged:" + event.getPath()
                                + ", new Data:" + new String(zkService.getZooKeeper().getData(event.getPath(), false, null)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
        instance1.addWatcherHandler(Watcher.Event.EventType.NodeDeleted,
                (event, zkService) -> {
                    System.out.println("NodeDeleted:" + event.getPath());
                    return true;
                });
        zkServiceImpl.addWatch("/test02", instance);

        // 修改值
        zkServiceImpl.getZooKeeper().setData("/test01", "t01".getBytes(), -1);
        zkServiceImpl.getZooKeeper().setData("/test02", "t0202".getBytes(), -1);

        Thread.sleep(3 * 1000);
        // 修改值
        zkServiceImpl.getZooKeeper().setData("/test01", "t0101".getBytes(), -1);
        zkServiceImpl.getZooKeeper().setData("/test02", "t020202".getBytes(), -1);
        Thread.sleep(3 * 1000);
        // 修改值
        zkServiceImpl.getZooKeeper().setData("/test01", "t010101".getBytes(), -1);
        zkServiceImpl.getZooKeeper().setData("/test02", "t020202".getBytes(), -1);
        Thread.sleep(3 * 1000);
        // 删除
        zkServiceImpl.getZooKeeper().delete("/test01", -1);
        zkServiceImpl.getZooKeeper().delete("/test02", -1);
//        new CountDownLatch(1).await();
        Thread.sleep(3 * 1000);
        System.out.println("==============end");
    }
}
