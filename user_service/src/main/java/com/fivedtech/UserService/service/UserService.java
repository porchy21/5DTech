package com.fivedtech.UserService.service;

import com.fivedtech.UserService.dto.CompanyDto;
import com.fivedtech.UserService.entity.User;
import com.fivedtech.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CompanyService companyService;

    public User addUser(User user) {
        companyService.getCompany(user.getCompanyId());
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        getUserById(user.getId());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with this id was not found"));
    }

    public CompanyDto getUserCompany(Long userId) {
        Long companyId = getUserById(userId).getCompanyId();
        return companyService.getCompany(companyId);
    }

    public List<User> getUsersByCompanyId(Long companyId) {
        List<User> users = userRepository.findUsersByCompanyId(companyId);
        if (users.isEmpty()) {
            throw new NoSuchElementException("Users with this company id were not found");
        }
        return users;
    }
}
