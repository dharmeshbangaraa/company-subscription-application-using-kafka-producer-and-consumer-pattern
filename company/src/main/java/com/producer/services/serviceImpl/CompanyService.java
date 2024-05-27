package com.producer.services.serviceImpl;

import com.producer.constants.ActionType;
import com.producer.constants.CompanyConstants;
import com.producer.constants.CompanyCreationConstants;
import com.producer.kafkaProducer.ProduceEvent;
import com.producer.model.Company;
import com.producer.model.dto.UpgradeRequestDto;
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
        company.setCurrentSku(CompanyCreationConstants.SIMPLE);
        company.setPreviousSku(CompanyCreationConstants.SIMPLE);
        Company savedCompany = this.companyRepository.save(company);
        return savedCompany.getRealmId();
    }

    @Override
    public void updateCompanySku(Company company) {
        Company companyObj = getByCompanyId(company.getRealmId());
        companyObj.setCurrentSku(company.getCurrentSku());
        companyObj.setPreviousSku(company.getPreviousSku());
        this.companyRepository.save(companyObj);
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
                UpgradeRequestDto upgradeRequestObj = new UpgradeRequestDto(company.getRealmId(), ActionType.UPGRADE, company.getCurrentSku(), company.getPreviousSku());
                boolean isEventProduced = produceEvent.sendData(upgradeRequestObj);
                if(isEventProduced) return String.format("action=EventProduce; status=Success; message=UpgradeEventProduced: %s", realmId);
                return String.format("action=EventProduce; status=Failed; message=UpgradeEventProduced: %s", realmId);
            }
        }
        return String.format("action=UpgradeCompany; status=Failed; message=SomethingWentWrong: %s", realmId);
    }

    @Override
    public Company getByCompanyId(Long companyId) {
        Optional<Company> optionalCompany = this.companyRepository.findById(companyId);
        return optionalCompany.orElse(null);
    }
}
