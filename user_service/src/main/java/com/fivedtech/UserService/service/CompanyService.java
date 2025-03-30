package com.fivedtech.UserService.service;

import com.fivedtech.UserService.client.CompanyServiceClient;
import com.fivedtech.UserService.dto.CompanyDto;
import com.fivedtech.UserService.exception.CompanyClientException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyServiceClient companyServiceClient;

    public CompanyDto getCompany(Long id) {
        try {
            ResponseEntity<CompanyDto> response = companyServiceClient.getCompanyById(id);
            return response.getBody();
        } catch (FeignException.NotFound e) {
            log.error("", e);
            throw new CompanyClientException("Company with this id not found");
        } catch (FeignException e) {
            throw new CompanyClientException("Company service error: " + e.contentUTF8());
        }
    }
}
