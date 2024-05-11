package com.producer.producerconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProperties {

    @Value("${event.producer.topic-name}")
    public String topicName;

    @Value("${spring.kafka.producer.bootstrap-servers}")
    public String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id}")
    public String groupId;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    public String autoCommit;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    public String offsetReset;


//    @Value("${spring.kafka.producer.partitions}")
//    public int partitions;
}
