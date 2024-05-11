package com.producerconsumer.billing.producerconfigs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class BillingProducerConfigs {

    @Bean
    public NewTopic produceEventTopic() {
        return TopicBuilder
                .name("premium-feature")
                .build();
    }

}
