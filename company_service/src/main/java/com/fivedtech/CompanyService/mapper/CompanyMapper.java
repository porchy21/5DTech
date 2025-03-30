package com.fivedtech.CompanyService.mapper;

import com.fivedtech.CompanyService.dto.CompanyDto;
import com.fivedtech.CompanyService.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDto);
}
