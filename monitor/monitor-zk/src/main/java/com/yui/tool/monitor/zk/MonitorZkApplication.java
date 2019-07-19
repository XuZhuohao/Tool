package com.yui.tool.monitor.zk;

import com.yui.tool.monitor.zk.Watcher.NodeChildrenChangedWatcher;
import com.yui.tool.monitor.zk.service.impl.DefaultZkHandler;
import com.yui.tool.monitor.zk.service.impl.ZkService;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * zk
 *
 * @author XuZhuohao
 * @date 2019/7/19
 */
//@SpringBootApplication
public class MonitorZkApplication {
    public static void main(String[] args) throws Exception {
//        SpringApplication.run(MonitorZkApplication.class);
        final ZkService zkService = ZkService.getInstance("127.0.0.1:2181", 5000, new DefaultZkHandler());
//        Thread.sleep(2*1000);
//        zkService.createEphemeral("/test", "mytest");
//        Thread.sleep(2*1000);
//        byte[] data = zkService.getZooKeeper().getData("/test", false, null);
//        System.out.println(new String(data));
//        Thread.sleep(2*1000);
//        zkService.getZooKeeper().setData("/test", "mytest02".getBytes(), -1);
//        Thread.sleep(2*1000);
//        data = zkService.getZooKeeper().getData("/test", false, null);
//        System.out.println(new String(data));
//        Thread.sleep(2*1000);
//        zkService.getZooKeeper().setData("/test", "mytest03".getBytes(), -1);
//        Thread.sleep(2*1000);
//        data = zkService.getZooKeeper().getData("/test", false, null);
//        System.out.println(new String(data));
//        int t1 = 0;
//        int t2 = 0;
//        byte[] result1;
//        byte[] result2;
//        zkService.createEphemeral("/test1", "t1" + t1++);
//        zkService.createEphemeral("/test2", "t2" + t2++);
//        zkService.getZooKeeper().setData("/test1", ("t1" + t1++).getBytes(), -1);
//        zkService.getZooKeeper().setData("/test2", ("t2" + t2++).getBytes(), -1);
//        result1 = zkService.getZooKeeper().getData("/test1", true, null);
//        result2 = zkService.getZooKeeper().getData("/test2", true, null);
//        System.out.println(new String(result1));
//        System.out.println(new String(result2));
//        zkService.getZooKeeper().setData("/test1", ("t1" + t1++).getBytes(), -1);
//        zkService.getZooKeeper().setData("/test2", ("t2" + t2++).getBytes(), -1);
//        result1 = zkService.getZooKeeper().getData("/test1", true, null);
//        result2 = zkService.getZooKeeper().getData("/test2", true, null);
//        System.out.println(new String(result1));
//        System.out.println(new String(result2));
//        zkService.getZooKeeper().setData("/test1", ("t1" + t1++).getBytes(), -1);
//        zkService.getZooKeeper().setData("/test2", ("t2" + t2++).getBytes(), -1);
//        result1 = zkService.getZooKeeper().getData("/test1", true, null);
//        result2 = zkService.getZooKeeper().getData("/test2", true, null);
//        System.out.println(new String(result1));
//        System.out.println(new String(result2));
        zkService.createEphemeral("/test1", "t1");
        zkService.addWatch("/", new NodeChildrenChangedWatcher());
        zkService.getZooKeeper().setData("/test1", "t2".getBytes(), -1);
        new CountDownLatch(1).await();

    }
}
