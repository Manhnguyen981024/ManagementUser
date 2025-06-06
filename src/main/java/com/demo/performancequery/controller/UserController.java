package com.demo.performancequery.controller;

import com.demo.performancequery.dto.RoleDTO;
import com.demo.performancequery.dto.UserDTO;
import com.demo.performancequery.entity.Role;
import com.demo.performancequery.entity.User;
import com.demo.performancequery.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Page<User>> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(userService.getUsers(page, size, sortBy));
    }

    @GetMapping("users/search")
    public ResponseEntity<Page<User>> findByName(@RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(userService.getUsersByName(name, page, size, sortBy));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users/{id}/roles")
    public ResponseEntity<Set<RoleDTO>> getUserRoles(@PathVariable Long id) {
        Set<RoleDTO> roles = userService.getRolesByUserId(id);
        if (roles == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userService.getRolesByUserId(id));
    }
}
