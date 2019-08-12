package com.yui.tool.monitor.kafka.client;


import com.yui.tool.monitor.kafka.dto.KafkaProperties;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartitionInfo;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class KafkaAdminClientCacheTest {
    private static AdminClient adminClient;
    @BeforeClass
    public static void beforeClass(){
        KafkaProperties kafkaProperties = new KafkaProperties();
        kafkaProperties.setClusters("xxx.xxx.xxx:port");
        kafkaProperties.setGroup("admin");
        final KafkaAdminClientCache kafkaAdminClientCache = KafkaAdminClientCache.getInstance(kafkaProperties.getProperties());
        adminClient = kafkaAdminClientCache.getAdminClient();
    }


    @Test
    public void describeTopics() throws Exception {
        final ListTopicsResult topics = adminClient.listTopics();
//        Set<String> names = topics.names().get();
        Set<String> names = new HashSet<>();
//        names.add("test_t1");
        names.add("test_02");
        displayLine();
        final DescribeTopicsResult topicsResult = adminClient.describeTopics(names);
        final Map<String, TopicDescription> allTopicInfo = topicsResult.all().get();
        allTopicInfo.forEach((key, value) -> System.out.println(String.format("key: %s , value: %s", key, value.toString())));
        allTopicInfo.forEach((key, value) -> {
            System.out.println("topic name is :" + key);
            final List<TopicPartitionInfo> partitions = value.partitions();
            // 打印分区信息
            partitions.forEach(info -> {
                System.out.println("partition: " + info.partition());
                System.out.println("leader: " + info.leader().toString());
                System.out.println("replicas: " + info.replicas().toString());
                System.out.println("isr: " + info.isr().toString());
                System.out.println("---------------");
            });
            System.out.println("---------------------------------------------");
        });

    }



    @Test
    public void getTopicInfo() throws Exception {
        final ListTopicsResult topics = adminClient.listTopics();
        displayLine();
        Set<String> names = topics.names().get();
        System.out.println("topic 个数 " + names.size());
        System.out.println("topic 列表： ");
        names.forEach(System.out::println);

        displayLine();
        final Collection<TopicListing> topicListings = topics.listings().get();
        topicListings.forEach(topic -> System.out.println(String.format("name: %s, infor:%s", topic.name(), topic.toString())));

        displayLine();
        final Map<String, TopicListing> topicListingMap = topics.namesToListings().get();
        topicListingMap.forEach((key, value) -> System.out.println(String.format("key:%s, value:%s", key, value.toString())));

        displayLine();
        final DescribeTopicsResult topicsResult = adminClient.describeTopics(names);
        final Map<String, TopicDescription> allTopicInfo = topicsResult.all().get();
        allTopicInfo.forEach((key, value) -> System.out.println(String.format("key: %s , value: %s", key, value.toString())));

    }

    private static void displayLine() {
        System.out.println("======================================");
    }

}
