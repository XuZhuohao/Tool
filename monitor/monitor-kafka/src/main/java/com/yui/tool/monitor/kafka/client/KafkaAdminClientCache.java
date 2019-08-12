package com.yui.tool.monitor.kafka.client;

import lombok.Getter;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 管理员客户端管理
 *
 * @author XuZhuohao
 * @date 2019/8/12
 */
@Getter
public class KafkaAdminClientCache {
    private static Map<String, KafkaAdminClientCache> KAFKA_CLIENT_CACHE = new HashMap<>(16);

    private AdminClient adminClient;

    private Properties properties;

    private KafkaAdminClientCache(AdminClient adminClient, Properties properties) {
        this.adminClient = adminClient;
        this.properties = properties;
    }

    public static KafkaAdminClientCache getInstance(Properties properties) {
        final String clusters = properties.getProperty("bootstrap.servers");
        if (KAFKA_CLIENT_CACHE.containsKey(clusters)) {
            return KAFKA_CLIENT_CACHE.get(clusters);
        }
        final AdminClient adminClient = KafkaAdminClient.create(properties);
        final KafkaAdminClientCache kafkaAdminClientCache = new KafkaAdminClientCache(adminClient, properties);
        KAFKA_CLIENT_CACHE.put(clusters, kafkaAdminClientCache);
        return kafkaAdminClientCache;
    }
}
