package com.producerconsumer.billing.services.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.producerconsumer.billing.models.Billing;
import com.producerconsumer.billing.models.PremiumFeature;
import com.producerconsumer.billing.services.IBillingService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@ConditionalOnProperty(value = "event.consumer.enabled", havingValue = "true")
@AllArgsConstructor
public class EventsConsumer {

    private IBillingService billingService;
    private final Logger logger = LoggerFactory.getLogger(EventsConsumer.class);
    private final ObjectMapper jsonObjectMapper = new ObjectMapper();
    private EventProcessor eventProcessor;
    private final PremiumFeature premiumFeature = new PremiumFeature();

    @KafkaListener(topics = "${event.consumer.topic-name}")
    public void receiveBillingEvent(ConsumerRecord<String, String> consumerRecord) {
        try {
            Billing billing = jsonObjectMapper.readValue(consumerRecord.value(), Billing.class);
            if (Objects.nonNull(billing)) {
                Long billingId = billingService.saveBillingEvent(billing);
                String actionType = billing.getActionType().toString();
                switch(actionType) {
                    case "UPGRADE" : eventProcessor.process(billing.getCompanyId());
                    break;
                    case "DOWNGRADE": break;
                    case "CANCEL" : break;
                    default: logger.info("action=processActionType; status:failed; reason=no billing action type found: {}", actionType);
                    break;
                }
                logger.info("action=processBillingEvent; status:success; topic={}; partition={}; companyId={}; actionType={}; offset={}",
                        consumerRecord.topic(), consumerRecord.partition(), billing.getCompanyId(), billing.getActionType(), consumerRecord.offset());
            }
        } catch (Exception ex) {
            logger.error("action=processBillingEvent; status=failed; reason={}", ex.getMessage());
        }
    }
}
