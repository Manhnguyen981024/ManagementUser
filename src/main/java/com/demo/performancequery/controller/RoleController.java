package com.demo.performancequery.controller;

import com.demo.performancequery.dto.RoleDTO;
import com.demo.performancequery.dto.UserDTO;
import com.demo.performancequery.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<Set<RoleDTO>> getRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/roles/{roleId}/users")
    public ResponseEntity<Set<UserDTO>> getRolesByUserId(@PathVariable Long roleId) {
        return ResponseEntity.ok(roleService.findAllUsers(roleId));
    }
}
