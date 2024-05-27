package com.producer.services.serviceImpl;

import com.producer.constants.CompanyConstants;
import com.producer.kafkaProducer.ProduceEvent;
import com.producer.model.Company;
import com.producer.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ProduceEvent produceEvent;

    @InjectMocks
    private CompanyService companyService;

    Company company = new Company(5000003L, "test company", CompanyConstants.LOWEST_SKU, CompanyConstants.LOWEST_SKU);
    Company company2 = new Company(5000004L, "Warehouse", CompanyConstants.HIGHEST_SKU, CompanyConstants.LOWEST_SKU);

    @Test
    @DisplayName("test should create a company on valid inputs")
    void createNewCompany() {
        when(companyRepository.save(any())).thenReturn(company);
        Long companyId = companyService.createNewCompany(company);
        Assertions.assertEquals(company.getRealmId(), companyId);
        verify(companyRepository, atMostOnce()).save(company);
    }

    @Test
    @DisplayName("test should update company sku on valid inputs")
    void updateCompanySku() {
        when(companyRepository.findById(any())).thenReturn(Optional.ofNullable(company2));
        companyService.updateCompanySku(company);
        verify(companyRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("test should return all companies")
    void getAllCompanies() {
        List<Company> companyList = List.of(company, company2);
        when(companyRepository.findAll()).thenReturn(companyList);
        List<Company> returnedCompanyList = companyService.getAllCompanies();
        Assertions.assertEquals(companyList, returnedCompanyList);
        Assertions.assertEquals(companyList.size(), returnedCompanyList.size());
    }

    @Test
    @DisplayName("test should produce event on successful upgrade event")
    void upgradeCompany() {
        when(companyRepository.findById(any())).thenReturn(Optional.of(company));
        when(produceEvent.sendData(any())).thenReturn(true);
        String message = companyService.upgradeCompany(company.getRealmId());
        Assertions.assertEquals(String.format("action=EventProduce; status=Success; message=UpgradeEventProduced: %s", company.getRealmId()), message);
    }

    @Test
    @DisplayName("test should return company on valid companyId")
    void getByCompanyId() {
        when(companyRepository.findById(any())).thenReturn(Optional.of(company));
        Company returnedCompany = companyService.getByCompanyId(company.getRealmId());
        Assertions.assertNotNull(returnedCompany);
        Assertions.assertEquals(company, returnedCompany);
    }

    @Test
    @DisplayName("test should return invalid message when current sku is highest sku")
    void upgradeHighestSkuCompany(){
        when(companyRepository.findById(any())).thenReturn(Optional.of(company2));
        String message = companyService.upgradeCompany(company2.getRealmId());
        verify(produceEvent, never()).sendData(any());
        Assertions.assertEquals(String.format("action=UpgradeCompany; status=Invalid; message=ADVANCED"), message);
    }

    @Test
    @DisplayName("test should not produce event on successful upgrade event")
    void upgradeCompanyFail() {
        when(companyRepository.findById(any())).thenReturn(Optional.of(company));
        when(produceEvent.sendData(any())).thenReturn(false);
        String message = companyService.upgradeCompany(company.getRealmId());
        Assertions.assertEquals(String.format("action=EventProduce; status=Failed; message=UpgradeEventProduced: %s", company.getRealmId()), message);
    }

    @Test
    @DisplayName("test should not produce event on unknown error or company id is not present")
    void upgradeCompanyFailOnCompanyIdNotPresent() {
        String message = companyService.upgradeCompany(company.getRealmId());
        Assertions.assertEquals(String.format("action=UpgradeCompany; status=Failed; message=SomethingWentWrong: %s", company.getRealmId()), message);
    }
}