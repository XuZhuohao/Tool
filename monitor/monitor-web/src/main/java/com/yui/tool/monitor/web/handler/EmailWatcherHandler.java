package com.yui.tool.monitor.web.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yui.tool.monitor.web.dto.WatcherHandlerInfoDto;
import com.yui.tool.monitor.zk.service.WatcherHandler;
import com.yui.tool.monitor.zk.watcher.ZkService;
import lombok.Data;
import org.apache.zookeeper.WatchedEvent;

/**
 * 邮箱发送handler
 *
 * @author XuZhuohao
 * @date 2019/7/25
 */
@WathcerHandler(name = "邮件通知", desc = "通过邮件通知对应的事件发生", initClass = EmailWatcherHandler.EmailVo.class)
public class EmailWatcherHandler implements WatcherHandler {

    private EmailVo emailVo;

    @Override
    public boolean process(WatchedEvent event, ZkService zkService) {
        System.out.println(JSON.toJSONString(emailVo));
        return false;
    }

    @Override
    public void init(String handlerInitObj) {
        this.emailVo = JSONObject.parseObject(handlerInitObj, EmailVo.class);
    }


    @Data
    static class EmailVo {
        private String fromAccount;
        private String fromPassword;
        private String toAccount;
        private String subject;
        private String context;
    }




}
