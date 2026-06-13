package com.example.back_ecole.repository;

import com.example.back_ecole.model.UserRole;
import com.example.back_ecole.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
