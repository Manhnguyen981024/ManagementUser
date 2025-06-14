package com.demo.performancequery.repository;

import com.demo.performancequery.entity.Role;
import com.demo.performancequery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findUsersById(Long id);
}
