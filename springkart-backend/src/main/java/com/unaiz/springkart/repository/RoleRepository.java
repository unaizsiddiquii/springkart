package com.unaiz.springkart.repository;

import com.unaiz.springkart.entity.AppRole;
import com.unaiz.springkart.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(AppRole roleName);
}