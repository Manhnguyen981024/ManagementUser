package com.demo.performancequery.service;

import com.demo.performancequery.entity.Role;
import com.demo.performancequery.entity.User;
import com.demo.performancequery.repository.RoleRepository;
import com.demo.performancequery.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class RandomRoleAssigner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Random random = new Random();

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void assignRandomRolesToUsers() {
        List<Role> allRoles = roleRepository.findAll();
        if (allRoles.isEmpty()) return;

        List<User> users = userRepository.findAll();

        for (User user : users) {
            // Clear old roles (optional)
            user.getRoles().clear();

            // Assign 1-3 random roles
            int roleCount = 1 + random.nextInt(2);
            user.getRoles().add(allRoles.get(roleCount));
        }

        userRepository.saveAll(users);
        System.out.println("✅ Assigned random roles to users");
    }
}
