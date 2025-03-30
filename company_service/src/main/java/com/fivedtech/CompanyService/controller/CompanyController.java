package com.fivedtech.CompanyService.controller;

import com.fivedtech.CompanyService.dto.UserDto;
import com.fivedtech.CompanyService.dto.CompanyDto;
import com.fivedtech.CompanyService.entity.Company;
import com.fivedtech.CompanyService.mapper.CompanyMapper;
import com.fivedtech.CompanyService.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Company API",
        description = "Endpoints for managing companies")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
@RestController
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @Operation(
            summary = "Add a new company",
            description = "Adds a new company to the database")
    @PostMapping
    public ResponseEntity<CompanyDto> addCompany(
            @RequestBody @Valid CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        Company savedCompany = companyService.addCompany(company);
        return ResponseEntity.ok(companyMapper.toDto(savedCompany));
    }

    @Operation(
            summary = "Update an existing company",
            description = "Updates the company details")
    @PatchMapping
    public ResponseEntity<CompanyDto> updateCompany(
            @RequestBody @Valid CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        companyDto = companyMapper.toDto(companyService.updateCompany(company));
        return ResponseEntity.ok(companyDto);
    }

    @Operation(
            summary = "Delete a company",
            description = "Deletes a company by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(
            @Parameter(description = "ID of the company to be deleted")
            @PathVariable @Positive Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get a company by ID",
            description = "Retrieves the company details by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(
            @Parameter(description = "ID of the company to be fetched")
            @PathVariable @Positive Long id) {
        CompanyDto company = companyMapper.toDto(companyService.getCompanyById(id));
        return ResponseEntity.ok(company);
    }

    @Operation(
            summary = "Get users in a company",
            description = "Retrieves a list of users working in the company")
    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserDto>> getUsersFromCompany(
            @Parameter(description = "ID of the company to fetch users")
            @PathVariable @Positive Long id) {
        List<UserDto> users = companyService.getUsersFromCompany(id);
        return ResponseEntity.ok(users);
    }
}
