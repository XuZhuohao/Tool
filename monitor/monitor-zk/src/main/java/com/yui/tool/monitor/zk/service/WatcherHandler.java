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
     * @param zkService zkService 实例
     * @return 处理结果，返回结果为 true ,会被重新添加监听，即下次再发生变动的时候，再次处理
     */
    boolean process(WatchedEvent event, ZkService zkService);

    /**
     * 初始化入口
     * @param handlerInitObj 初始化对象
     */
    default void init(String handlerInitObj){

    }
}
