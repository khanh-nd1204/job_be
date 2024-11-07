package com.project.job.service;

import com.project.job.dto.company.CreateCompanyDTO;
import com.project.job.dto.company.UpdateCompanyDTO;
import com.project.job.entity.CompanyEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    CompanyEntity createCompany(CreateCompanyDTO createCompanyDTO) throws Exception;
    CompanyEntity updateCompany(UpdateCompanyDTO updateCompanyDTO) throws Exception;
    List<CompanyEntity> getCompanies() throws Exception;
    CompanyEntity getCompanyById(Long id) throws Exception;
    void deleteCompanyById(Long id) throws Exception;
}
