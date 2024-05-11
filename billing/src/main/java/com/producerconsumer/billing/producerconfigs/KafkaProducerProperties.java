package com.producerconsumer.billing.producerconfigs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerProperties {

//    @Value("${event.producer.topic-name}")
//    public String topicName;

    @Value("${spring.kafka.producer.bootstrap-servers}")
    public String bootstrapServer;

}
