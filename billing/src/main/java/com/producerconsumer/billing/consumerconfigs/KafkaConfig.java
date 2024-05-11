package com.producerconsumer.billing.consumerconfigs;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(value = "event.consumer.enabled", havingValue = "true")
public class KafkaConfig {

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> consumerListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> consumerListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
//        consumerListenerContainerFactory.setConsumerFactory(kafkaConsumerFactory());
//        return consumerListenerContainerFactory;
//    }

    public ConsumerFactory<String, Object> kafkaConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfigProperties.bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConfigProperties.groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConfigProperties.offsetReset);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KafkaConfigProperties.autoCommit);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

}
