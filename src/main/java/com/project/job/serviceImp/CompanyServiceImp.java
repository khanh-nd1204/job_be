package com.project.job.serviceImp;

import com.project.job.dto.company.CreateCompanyDTO;
import com.project.job.dto.company.UpdateCompanyDTO;
import com.project.job.entity.CompanyEntity;
import com.project.job.repository.CompanyRepo;
import com.project.job.service.CompanyService;
import com.project.job.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyServiceImp implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public CompanyEntity createCompany(CreateCompanyDTO createCompanyDTO) throws Exception {
        CompanyEntity company = new CompanyEntity();
        company.setName(createCompanyDTO.getName().trim());
        company.setAddress(createCompanyDTO.getAddress().trim());
        company.setDescription(createCompanyDTO.getDescription().trim());
        return companyRepo.save(company);
    }

    @Override
    public CompanyEntity updateCompany(UpdateCompanyDTO updateCompanyDTO) throws Exception {
        CompanyEntity company = companyRepo.findById(updateCompanyDTO.getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));
        company.setName(updateCompanyDTO.getName().trim());
        company.setAddress(updateCompanyDTO.getAddress().trim());
        company.setDescription(updateCompanyDTO.getDescription().trim());
        if (!updateCompanyDTO.getLogo().isBlank()) {
            company.setLogo(updateCompanyDTO.getLogo().trim());
        }
        return companyRepo.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyEntity> getCompanies() throws Exception {
        return companyRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyEntity getCompanyById(Long id) throws Exception {
        return companyRepo.findById(id).orElseThrow(() -> new NotFoundException("Company not found"));
    }

    @Override
    public void deleteCompanyById(Long id) throws Exception {
        if (!companyRepo.existsById(id)) {
            throw new NotFoundException("Company not found");
        }
        companyRepo.deleteById(id);
    }
}
