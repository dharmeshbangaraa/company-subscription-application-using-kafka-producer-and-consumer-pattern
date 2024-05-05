package com.producer.services.serviceImpl;

import com.producer.constants.CompanyConstants;
import com.producer.constants.CompanyCreationConstants;
import com.producer.kafkaProducer.ProduceEvent;
import com.producer.model.Company;
import com.producer.repository.CompanyRepository;
import com.producer.services.ICompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService implements ICompanyService {

    private CompanyRepository companyRepository;

    private ProduceEvent produceEvent;

    @Override
    public Long createNewCompany(Company company) {
        company.setCurrentSku(CompanyCreationConstants.CURRENT_SKU);
        company.setPreviousSku(CompanyCreationConstants.PREVIOUS_SKU);
        this.companyRepository.save(company);
        return company.getRealmId();
    }

    @Override
    public List<Company> getAllCompanies() {
        return this.companyRepository.findAll();
    }

    @Override
    public String upgradeCompany(Long realmId) {
        Optional<Company> optionalCompany = this.companyRepository.findById(realmId);
        if(optionalCompany.isPresent()){
            Company company = optionalCompany.get();
            if(company.getCurrentSku().equals(CompanyConstants.HIGHEST_SKU)){
                return String.format("action=UpgradeCompany; status=Invalid; message=%s", company.getCurrentSku());
            }
            else{
                boolean isEventProduced = produceEvent.sendData("Upgrade Event produced");
                if(isEventProduced) return String.format("action=EventProduce; status=Success; message=UpgradeEventProduced: %s", realmId);
                return String.format("action=EventProduce; status=Failed; message=UpgradeEventProduced: %s", realmId);
            }
        }
        return String.format("action=UpgradeCompany; status=Failed; message=SomethingWentWrong: %s", realmId);

    }
}
