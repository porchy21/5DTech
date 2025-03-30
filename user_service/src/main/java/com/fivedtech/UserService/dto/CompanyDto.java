package com.fivedtech.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CompanyDto {
    private Long id;
    private String name;
    private String contactPhone;
    private Long budget;
}
