package com.fivedtech.CompanyService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyDto {

    @Schema(description = "Unique identifier of the company", example = "1", nullable = true)
    @Positive
    private Long id;

    @Schema(description = "Name of the company", example = "Acme Corporation")
    @NotNull
    @Size(max = 64)
    private String name;

    @Schema(description = "Contact phone number of the company", example = "+1-800-555-1234")
    @NotNull
    @Size(max = 32)
    private String contactPhone;

    @Schema(description = "Budget allocated for the company", example = "500000")
    @NotNull
    private Long budget;
}
