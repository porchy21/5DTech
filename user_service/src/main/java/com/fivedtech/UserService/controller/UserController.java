package com.fivedtech.UserService.controller;

import com.fivedtech.UserService.dto.CompanyDto;
import com.fivedtech.UserService.dto.UserDto;
import com.fivedtech.UserService.entity.User;
import com.fivedtech.UserService.mapper.UserMapper;
import com.fivedtech.UserService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        name = "Users API",
        description = "Endpoints for interactions with users")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Get a user by ID",
            description = "Retrieve a user by their unique ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "ID of the user to be retrieved")
            @PathVariable @Positive Long id) {
        UserDto userDto = userMapper.toDto(userService.getUserById(id));
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Get the company of a user",
            description = "Retrieve the company associated with a user by their unique ID"
    )
    @GetMapping("/{id}/company")
    public ResponseEntity<CompanyDto> getUserCompany(
            @Parameter(description = "ID of the user to retrieve the company for")
            @PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.getUserCompany(id));
    }

    @Operation(
            summary = "Get all users by company ID",
            description = "Retrieve a list of users associated with a specific company"
    )
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<UserDto>> getUsersByCompanyId(
            @Parameter(description = "ID of the company to retrieve users for")
            @PathVariable @Positive
            Long companyId) {
        List<UserDto> users = userMapper.toDto(userService.getUsersByCompanyId(companyId));
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Add a new user",
            description = "Add a new user to the system"
    )
    @PostMapping
    public ResponseEntity<UserDto> addUser(
            @Parameter(description = "User object to be added")
            @RequestBody @Validated UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userDto = userMapper.toDto(userService.addUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @Operation(
            summary = "Update an existing user",
            description = "Update an existing user's information in the system"
    )
    @PatchMapping
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "User object with updated information")
            @RequestBody @Validated UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userDto = userMapper.toDto(userService.updateUser(user));
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Delete a user by ID",
            description = "Delete a user from the system by their unique ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to be deleted")
            @PathVariable @Positive Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
