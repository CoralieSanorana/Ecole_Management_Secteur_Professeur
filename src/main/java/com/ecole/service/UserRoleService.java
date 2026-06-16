package service;

import entity.UserRole;
import entity.UserRoleId;
import repository.UserRoleRepository;
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
