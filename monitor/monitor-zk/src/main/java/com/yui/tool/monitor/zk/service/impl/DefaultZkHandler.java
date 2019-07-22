package com.yui.tool.monitor.zk.service.impl;

import com.alibaba.fastjson.JSON;
import com.yui.tool.monitor.zk.service.ZkHandler;
import com.yui.tool.monitor.zk.watcher.ZkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;

/**
 * 默认zk处理
 *
 * @author XuZhuohao
 * @date 2019/7/19
 */
@Slf4j
public class DefaultZkHandler implements ZkHandler {
    @Override
    public void established(WatchedEvent event, ZkService zkService) {
        log.info("zookeeper session established:%s", JSON.toJSONString(zkService));
    }
}
