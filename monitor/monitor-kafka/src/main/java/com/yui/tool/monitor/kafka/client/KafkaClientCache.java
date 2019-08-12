package com.yui.tool.monitor.kafka.client;

import lombok.Getter;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 普通客户端管理
 *
 * @author XuZhuohao
 * @date 2019/8/12
 */
@Getter
public class KafkaClientCache<T, V> {
    private static Map<String, KafkaClientCache> KAFKA_CLIENT_CACHE = new HashMap<>(16);

    private KafkaConsumer<T, V>  kafkaConsumer;

    private Properties properties;

    private KafkaClientCache(Properties properties) {
        this.properties = properties;
        this.setKafkaConsumer();
    }

    public static <T1, V1> KafkaClientCache<T1, V1> getInstance(Properties properties) {
        final String clusters = properties.getProperty("bootstrap.servers");
        final String group = properties.getProperty("group.id");
        if (KAFKA_CLIENT_CACHE.containsKey(clusters + group)) {
            return KAFKA_CLIENT_CACHE.get(clusters);
        }
        final KafkaClientCache<T1, V1> kafkaAdminClientCache = new KafkaClientCache<>(properties);
        KAFKA_CLIENT_CACHE.put(clusters + group, kafkaAdminClientCache);
        return kafkaAdminClientCache;
    }

    public KafkaConsumer<T, V> getKafkaConsumer(){
        return kafkaConsumer;
    }

    private synchronized void setKafkaConsumer(){
        this.kafkaConsumer = new KafkaConsumer<>(properties);
    }
}
