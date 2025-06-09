package com.demo.performancequery.service;

import com.demo.performancequery.dto.RoleDTO;
import com.demo.performancequery.dto.UserDTO;
import com.demo.performancequery.entity.User;
import com.demo.performancequery.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class RoleService {
    private final RoleRepository roleRepository;

    public Set<RoleDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(r -> new RoleDTO(r.getId(), r.getName()))
                .collect(Collectors.toSet());
    }

    public Set<UserDTO> findAllUsers(Long roleId) {
        log.info("All users in roles1 : {} " , roleRepository.findById(roleId).orElse(null));
        return roleRepository.findUsersById(roleId)
                .stream()
                .map(r -> {
                    Set<User> users = r.getUsers();
                    return users.stream()
                            .map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail()))
                            .collect(Collectors.toSet());
                }).findAny().get();
    }
}
