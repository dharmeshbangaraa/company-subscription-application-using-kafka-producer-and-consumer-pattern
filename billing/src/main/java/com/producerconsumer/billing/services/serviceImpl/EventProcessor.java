package com.producerconsumer.billing.services.serviceImpl;

import com.producerconsumer.billing.models.PremiumFeature;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventProcessor {

    private final Logger logger = LoggerFactory.getLogger(EventProcessor.class);
    private KafkaTemplate<String, Object> template;
    private final PremiumFeature premiumFeature = new PremiumFeature();

    public void process(Long companyId) {
        try {
            premiumFeature.setCompanyId(companyId);
            template.send("premium-feature", premiumFeature);
            logger.info("action=handleEventProcess; status:success; message={}", premiumFeature.getFeatureName());
        } catch (Exception ex) {
            logger.error("action=handleEventProcess; status:failed; reason={}", ex.getMessage());
        }
    }
}
