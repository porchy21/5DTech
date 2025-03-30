package com.fivedtech.CompanyService.service;

import com.fivedtech.CompanyService.client.UserServiceClient;
import com.fivedtech.CompanyService.dto.UserDto;
import com.fivedtech.CompanyService.exception.UserClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserServiceClient userServiceClient;

    public List<UserDto> getUsersFromCompany(Long companyId) {
        ResponseEntity<List<UserDto>> response = userServiceClient.getUsersByCompanyId(companyId);

        if (response.getStatusCode().isError() || response.getBody() == null) {
            log.error("Getting the list of users failed. Response status: {}",
                    response.getStatusCode());
            throw new UserClientException("User client failed");
        }

        return response.getBody();
    }
}
