package com.yui.tool.monitor.kafka.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * kafka 配置消息
 * 参考 http://kafka.apache.org/documentation/#consumerconfigs
 *
 * @author XuZhuohao
 * @date 2019/8/12
 */
@Setter
@Getter
@Accessors(chain = true)
public class KafkaProperties {
    /**
     * 集群信息
     */
    private String clusters;
    /**
     * 组名
     */
    private String group;
    /**
     * session 超时时间(ms), 默认 120000ms
     */
    private String sessionTimeoutMs = "120000";
    /**
     * 请求超时时间(ms), 默认 160000ms
     */
    private String requestTimeoutMs = "160000";
    /**
     * fetch 最大等待时间（ms）， 默认 5000ms
     */
    private String fetchMaxWaitMs = "5000";

    /**
     * 最大的拉取记录数， 默认10条
     */
    private String maxPollRecords = "10";

    /**
     * 拓展的配置= =可覆盖默认配置
     */
    private Map<String, String> propertiesData = new HashMap<>(16);

    /**
     * 从当前数据获取配置
     * @return kafka Properties
     */
    public Properties getProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", this.clusters);
        props.put("group.id", this.group);
        props.put("enable.auto.commit", false);
        props.put("session.timeout.ms", this.sessionTimeoutMs);
        props.put("request.timeout.ms", this.requestTimeoutMs);
        props.put("fetch.max.wait.ms", this.fetchMaxWaitMs);
        props.put("auto.offset.reset", "earliest");
        props.put("max.poll.records", this.maxPollRecords);
        props.put("max.partition.fetch.bytes", "5048576");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.putAll(propertiesData);
        return props;
    }

}
