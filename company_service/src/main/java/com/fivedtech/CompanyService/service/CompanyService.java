package com.fivedtech.CompanyService.service;

import com.fivedtech.CompanyService.dto.UserDto;
import com.fivedtech.CompanyService.entity.Company;
import com.fivedtech.CompanyService.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserService userService;

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company) {
        getCompanyById(company.getId());
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public List<UserDto> getUsersFromCompany(Long id) {
        getCompanyById(id);
        return userService.getUsersFromCompany(id);
    }

    @Transactional(readOnly = true)
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Company with this id was not found"));
    }
}
