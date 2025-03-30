package com.fivedtech.UserService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @Schema(description = "Unique identifier of the user", example = "1", nullable = true)
    @Positive
    private Long id;

    @Schema(description = "First name of the user", example = "John")
    @NotNull
    @Size(max = 32)
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    @NotNull
    @Size(max = 32)
    private String lastName;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "Company ID to which the user is assigned", example = "1")
    @NotNull
    @Positive
    private Long companyId;
}
