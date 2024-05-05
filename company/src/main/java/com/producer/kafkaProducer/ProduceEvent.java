package com.producer.kafkaProducer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProduceEvent {

    private final Logger logger = LoggerFactory.getLogger(ProduceEvent.class);
    private KafkaTemplate<String, String> template;

    public boolean sendData(String msg) {
        template.send("billing-topic", msg);
        logger.info("Produced Billing Event");
        return true;
    }

}
