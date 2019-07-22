## 1.创建一个 zk 服务
com.yui.tool.monitor.zk.watcher.ZkService  
理论上，一个 zookeeper 链接对应一个 zkService，但是代码上不做控制
### 1.1 初始化
```java
    public class ZkService{
        /**
         * 获取实例
         * @param address zookeeper 地址
         * @param timeout 超时时间，单位毫秒
         * @param zkHandler  ZkHandler 实现,用于处理连接成功后的处理
         * @return zkService
         */
        public static  getInstance(String address, int timeout, ZkHandler zkHandler) {
            final ZkService zkService = new ZkService();
            zkService.setZkHandler(zkHandler);
            zkService.init(address, timeout);
            return zkService;
        }
    }
```
### 1.2 代码示例
```$java
final ZkService zkServiceImpl = ZkService.getInstance("127.0.0.1:2181", 5000, new DefaultZkHandler());
```

## 2.添加 Watcher
com.yui.tool.monitor.zk.watcher.ZkWatcher
同一个连接且同一个 path 对应一个 ZkWatcher
### 2.1 初始化
**使用 zkServiceImpl.getZkWatcher(path); 获取**
```java
public class ZkWatcher{
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
}
```
### 2.2 添加一个监视类型
```
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
```
### 2.3 代码示例
```
        /添加监控节点 /test01，监控事件为 NodeDataChanged，NodeDeleted
        // 1.获取一个实例
        final ZkWatcher instance = zkServiceImpl.getZkWatcher("/test01");
        // 2.添加一个处理，事件为 NodeDataChanged
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
        //3.添加一个处理， 事件类型为 NodeDeleted
        instance.addWatcherHandler(Watcher.Event.EventType.NodeDeleted,
                (event, zkService) -> {
                    System.out.println("NodeDeleted:" + event.getPath());
                    return true;
                });
        //4.添加一个处理，事件为  NodeDataChanged
        instance.addWatcherHandler(Watcher.Event.EventType.NodeDataChanged, new ExampleNodeDataChangedWathcerHandlerImpl("/test01", zkServiceImpl));
```

## 3.WatcherHandler 说明
```java
/**
 * watcher 动作借口
 *
 * @author XuZhuohao
 * @date 2019/7/22
 */
public interface WatcherHandler {
    /**
     * 处理过程
     * @param event watch 处理事件
     * @param zkService zkService 实例
     * @return 处理结果，返回结果为 true ,会被重新添加监听，即下次再发生变动的时候，再次处理
     */
    boolean process(WatchedEvent event, ZkService zkService);
}
```
### 3.1 接口实现举例
```java
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

```




