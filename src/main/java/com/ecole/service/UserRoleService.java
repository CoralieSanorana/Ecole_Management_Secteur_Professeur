package com.example.back_ecole.service;

import com.example.back_ecole.model.UserRole;
import com.example.back_ecole.model.UserRoleId;
import com.example.back_ecole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    public Optional<UserRole> findById(UserRoleId id) {
        return userRoleRepository.findById(id);
    }

    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public void deleteById(UserRoleId id) {
        userRoleRepository.deleteById(id);
    }
}
