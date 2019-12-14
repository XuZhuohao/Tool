package com.yui.tool.monitor.web.controller;

import com.yui.tool.monitor.web.cache.HandlerCache;
import com.yui.tool.monitor.web.dto.WatcherHandlerInfoDto;
import com.yui.tool.monitor.web.service.ZkMonitorService;
import com.yui.tool.monitor.zk.watcher.ZkService;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * zk 监控
 *
 * @author XuZhuohao
 * @date 2019/7/23
 */
@RestController
@RequestMapping("/zk")
public class ZkMonitorController {
    @Autowired
    ZkMonitorService zkMonitorService;

    @PostMapping("/service/add")
    public String addZkService(String key, String address, int timeout){
        zkMonitorService.addZkService(key, address, timeout);
        return "success";
    }
    @PostMapping("/watcher/add")
    public String addZkWatcher(String path, String serviceKey, String watcherHandlerKey, String initHandlerVo){
        zkMonitorService.addZkWatcher(Watcher.Event.EventType.NodeChildrenChanged, path, serviceKey,watcherHandlerKey,initHandlerVo);
        return "success";
    }
    @GetMapping("/service/get")
    public List<ZkService> getService(){
        return zkMonitorService.getAllZkServices();
    }
    @GetMapping("/watcher/get")
    public Map<String, WatcherHandlerInfoDto> getWatcher(){
        return HandlerCache.getWatcherHandlerInfo();
    }


}
