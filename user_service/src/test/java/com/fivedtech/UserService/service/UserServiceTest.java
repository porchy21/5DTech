package com.fivedtech.UserService.service;

import com.fivedtech.UserService.dto.CompanyDto;
import com.fivedtech.UserService.entity.User;
import com.fivedtech.UserService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .companyId(123L)
                .build();
    }

    @Test
    void testAddUser_Success() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getCompanyId(), result.getCompanyId());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_Success() {
        User updatedUser = new User(1L, "John", "Does", "john.doe.updated@example.com", 321L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getFirstName(), result.getFirstName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getCompanyId(), result.getCompanyId());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User updatedUser = new User(1L, "John", "Doe", "john.doe.updated@example.com", 321L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(updatedUser));

        assertEquals("User with this id was not found", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        Long userId = 1L;

        doThrow(new IllegalArgumentException("User with this id was not found"))
                .when(userRepository).deleteById(userId);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUser(userId));

        assertEquals("User with this id was not found", exception.getMessage());

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testGetUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getCompanyId(), result.getCompanyId());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.getUserById(1L));

        assertEquals("User with this id was not found", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserCompany_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(companyService.getCompany(user.getCompanyId())).thenReturn(new CompanyDto(1L, "TechCorp", "+347328324", 10000L));

        CompanyDto result = userService.getUserCompany(1L);

        assertNotNull(result);
        assertEquals("TechCorp", result.getName());

        verify(userRepository, times(1)).findById(1L);
        verify(companyService, times(1)).getCompany(user.getCompanyId());
    }

    @Test
    void testGetUserCompany_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.getUserCompany(1L));

        assertEquals("User with this id was not found", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
        verify(companyService, times(0)).getCompany(anyLong());
    }

    @Test
    void testGetUsersByCompanyId_UsersExist() {
        List<User> users = Arrays.asList(user, new User(2L, "Jane", "Doe", "jane.doe@example.com", 123L));
        when(userRepository.findUsersByCompanyId(123L)).thenReturn(users);

        List<User> result = userService.getUsersByCompanyId(123L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());

        verify(userRepository, times(1)).findUsersByCompanyId(123L);
    }

    @Test
    void testGetUsersByCompanyId_UsersNotFound() {
        when(userRepository.findUsersByCompanyId(123L)).thenReturn(Collections.emptyList());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.getUsersByCompanyId(123L));

        assertEquals("Users with this company id were not found", exception.getMessage());

        verify(userRepository, times(1)).findUsersByCompanyId(123L);
    }
}
