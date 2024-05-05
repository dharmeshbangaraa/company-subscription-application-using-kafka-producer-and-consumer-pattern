package com.producer.controller;

import com.producer.model.Company;
import com.producer.services.ICompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private ICompanyService iCompanyService;

    @PostMapping("/")
    public ResponseEntity<String> createCompany(@RequestBody Company company){
        Optional<Long> realmId = Optional.of(this.iCompanyService.createNewCompany(company));
        if(realmId.isPresent()){
            return ResponseEntity.ok(String.format("action=CreateCompany; status=success; message=CompanyCreatedWithCompanyId: %s", realmId.get()));
        }
        return new ResponseEntity<>("action=CreateCompany; status=failed; message=CompanyCreationFailed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<List<Company>> getCompanies(){
        return ResponseEntity.ok(this.iCompanyService.getAllCompanies());
    }

    @PostMapping("/upgrade")
    public ResponseEntity<String> upgradeCompany(@RequestParam Long realmId){
        return ResponseEntity.ok(this.iCompanyService.upgradeCompany(realmId));
    }

}
