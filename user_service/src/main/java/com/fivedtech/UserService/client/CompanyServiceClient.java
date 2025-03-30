package com.fivedtech.UserService.client;

import com.fivedtech.UserService.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", url = "http://${company-service.host}:${company-service.port}")
public interface CompanyServiceClient {

    @GetMapping("/api/v1/companies/{id}")
    ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id);
}
