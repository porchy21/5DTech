package com.fivedtech.CompanyService.client;

import com.fivedtech.CompanyService.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", url = "http://${user-service.host}:${user-service.port}")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/company/{companyId}")
    public ResponseEntity<List<UserDto>> getUsersByCompanyId(@PathVariable Long companyId);
}
