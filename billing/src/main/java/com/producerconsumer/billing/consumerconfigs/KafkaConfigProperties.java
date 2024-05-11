package com.producerconsumer.billing.consumerconfigs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnProperty(value = "event.consumer.enabled", havingValue = "true")
public class KafkaConfigProperties {

//    @Value("${event.consumer.topic-name}")
//    public static String topicName;
//
//    @Value("${event.consumer.enabled}")
//    public static String consumerEnabled;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    public static String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id}")
    public static String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    public static String offsetReset;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    public static String autoCommit;

}
