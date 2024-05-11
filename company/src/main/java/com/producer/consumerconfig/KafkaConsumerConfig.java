package com.producer.consumerconfig;

import com.producer.producerconfig.KafkaProperties;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class KafkaConsumerConfig {

    private KafkaProperties kafkaProperties;

    public ConsumerFactory<String, Object> kafkaConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.offsetReset);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.autoCommit);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }
}
