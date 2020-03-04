package com.yui.tool.dynamicdubbo.config;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XuZhuohao
 * @date 2020-03-04 14:45
 */
@Configuration
public class InitReferenceConfig {

    @Bean(name = "reference")
    public ReferenceConfig<GenericService> getReferenceConfig(){
        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        return new ReferenceConfig<>();
    }
}
