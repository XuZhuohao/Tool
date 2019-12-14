package com.yui.tool.monitor.kafka.client;


import com.yui.tool.monitor.kafka.dto.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.util.Collections;

public class KafkaClientCacheTest {
    private static KafkaConsumer<String, String> kafkaConsumer;

    @BeforeClass
    public static void beforeClass() {
        KafkaProperties kafkaProperties = new KafkaProperties();
        kafkaProperties.setClusters("xxx.xxx.xxx:port");
        kafkaProperties.setGroup("admin");
        final KafkaClientCache<String, String> kafkaClientCache = KafkaClientCache.getInstance(kafkaProperties.getProperties());
        kafkaConsumer = kafkaClientCache.getKafkaConsumer();
    }

    @Test
    public void pull() {
        kafkaConsumer.subscribe(Collections.singletonList("__consumer_offsets"));
        final ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
        records.forEach(consumerRecord-> System.out.println(String.format("key: %s, value: %s, \n infor: %s", consumerRecord.key(), consumerRecord.value(), consumerRecord.toString())));
    }

}
