package com.project.job.controller;

import com.project.job.dto.ResponseObject;
import com.project.job.dto.company.CreateCompanyDTO;
import com.project.job.dto.company.UpdateCompanyDTO;
import com.project.job.entity.CompanyEntity;
import com.project.job.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyCtrl {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<ResponseObject> createCompany(@Valid @RequestBody CreateCompanyDTO createCompanyDTO) throws Exception {
        CompanyEntity company = companyService.createCompany(createCompanyDTO);
        ResponseObject res = new ResponseObject(HttpStatus.CREATED.value(),
                "Company created successfully", company, null
        );
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseObject> updateCompany(@Valid @RequestBody UpdateCompanyDTO updateCompanyDTO) throws Exception {
        CompanyEntity company = companyService.updateCompany(updateCompanyDTO);
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                "Company updated successfully", company, null
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getCompanies() throws Exception {
        List<CompanyEntity> companies = companyService.getCompanies();
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                "Companies fetched successfully", companies, null
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getCompanyById(@PathVariable Long id) throws Exception {
        CompanyEntity company = companyService.getCompanyById(id);
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                "Company fetched successfully", company, null
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseObject> deleteCompany(@PathVariable Long id) throws Exception {
        companyService.deleteCompanyById(id);
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                "Company deleted successfully", null, null
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
