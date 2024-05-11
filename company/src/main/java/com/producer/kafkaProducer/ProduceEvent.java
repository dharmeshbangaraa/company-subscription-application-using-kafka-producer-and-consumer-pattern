package com.producer.kafkaProducer;

import com.producer.model.dto.UpgradeRequestDto;
import com.producer.producerconfig.KafkaProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProduceEvent {

    private final Logger logger = LoggerFactory.getLogger(ProduceEvent.class);

    private KafkaProperties kafkaProperties;
    private KafkaTemplate<String, Object> template;
    public boolean sendData(UpgradeRequestDto upgradeRequestDto) {
        template.send(kafkaProperties.topicName, upgradeRequestDto);
        logger.info("Produced Billing Event");
        return true;
    }
}
