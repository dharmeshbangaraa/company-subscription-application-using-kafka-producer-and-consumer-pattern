package com.producer.services;

import com.producer.model.Company;

import java.util.List;

public interface ICompanyService {

    Long createNewCompany(Company company);

    void updateCompanySku(Company company);

    List<Company> getAllCompanies();

    String upgradeCompany(Long realmId);

    Company getByCompanyId(Long companyId);

}
