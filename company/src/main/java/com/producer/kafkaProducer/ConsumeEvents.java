package com.producer.kafkaProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.producer.model.Company;
import com.producer.model.dto.PremiumFeature;
import com.producer.services.ICompanyService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.producer.constants.CompanyCreationConstants.ADVANCED;

@Service
@AllArgsConstructor
public class ConsumeEvents {

    private ICompanyService iCompanyService;
    private final Logger logger = LoggerFactory.getLogger(ConsumeEvents.class);
    private final ObjectMapper jsonObjectMapper = new ObjectMapper();

    @KafkaListener(topics = "premium-feature")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        try {
            PremiumFeature premiumFeatureObj = jsonObjectMapper.readValue(consumerRecord.value(), PremiumFeature.class);
            if(premiumFeatureObj.subscribed() && premiumFeatureObj.featureName().equals("ADVANCE STORAGE")) {
                Company company = iCompanyService.getByCompanyId(premiumFeatureObj.companyId());
                System.out.println(company.getCompanyName());
                company.setPreviousSku(company.getCurrentSku());
                company.setCurrentSku(ADVANCED);
                iCompanyService.updateCompanySku(company);
            }
            logger.info(premiumFeatureObj.featureName());
        } catch (Exception ex) {
            logger.error("action=receiveBillingResponse; status=failed; reason={}", ex.getMessage());
        }
    }

}
