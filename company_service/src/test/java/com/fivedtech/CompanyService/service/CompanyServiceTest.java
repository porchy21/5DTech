package com.fivedtech.CompanyService.service;

import com.fivedtech.CompanyService.dto.UserDto;
import com.fivedtech.CompanyService.entity.Company;
import com.fivedtech.CompanyService.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CompanyService companyService;

    private Company company;

    @BeforeEach
    void setUp() {
        company = Company.builder()
                .id(1L)
                .name("Test Company")
                .build();
    }

    @Test
    void testAddCompany_Success() {
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        Company result = companyService.addCompany(company);

        assertNotNull(result);
        assertEquals(company.getId(), result.getId());
        assertEquals(company.getName(), result.getName());
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testUpdateCompany_Success() {
        Company updatedCompany = Company.builder()
                .id(company.getId())
                .name("Updated company")
                .build();
        when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

        Company result = companyService.updateCompany(updatedCompany);

        assertNotNull(result);
        assertEquals(updatedCompany.getName(), result.getName());
        verify(companyRepository, times(1)).save(updatedCompany);
    }

    @Test
    void testUpdateCompany_CompanyNotFound() {
        Company updatedCompany = Company.builder()
                .id(company.getId())
                .name("Updated company")
                .build();
        when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            companyService.updateCompany(updatedCompany);
        });
        assertEquals("Company with this id was not found", exception.getMessage());
    }

    @Test
    void testDeleteCompany_Success() {
        doNothing().when(companyRepository).deleteById(company.getId());

        companyService.deleteCompany(company.getId());

        verify(companyRepository, times(1)).deleteById(company.getId());
    }

    @Test
    void testDeleteCompany_CompanyNotFound() {
        doThrow(new IllegalArgumentException("Company with this id was not found"))
                .when(companyRepository).deleteById(company.getId());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            companyService.deleteCompany(company.getId());
        });
        assertEquals("Company with this id was not found", exception.getMessage());
    }

    @Test
    void testGetUsersFromCompany_Success() {
        Long companyId = 1L;
        List<UserDto> userDtos = List.of(new UserDto(), new UserDto());
        when(userService.getUsersFromCompany(companyId)).thenReturn(userDtos);

        List<UserDto> result = companyService.getUsersFromCompany(companyId);

        assertNotNull(result);
        assertEquals(userDtos.size(), result.size());
        verify(userService, times(1)).getUsersFromCompany(companyId);
    }

    @Test
    void testGetCompanyById_Success() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

        Company result = companyService.getCompanyById(company.getId());

        assertNotNull(result);
        assertEquals(company.getId(), result.getId());
        assertEquals(company.getName(), result.getName());
        verify(companyRepository, times(1)).findById(company.getId());
    }

    @Test
    void testGetCompanyById_CompanyNotFound() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            companyService.getCompanyById(company.getId());
        });
        assertEquals("Company with this id was not found", exception.getMessage());
    }
}
