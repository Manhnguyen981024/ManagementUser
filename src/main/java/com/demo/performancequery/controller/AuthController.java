package com.demo.performancequery.controller;

import com.demo.performancequery.dto.LoginRequest;
import com.demo.performancequery.dto.UserDTO;
import com.demo.performancequery.entity.User;
import com.demo.performancequery.repository.UserRepository;
import com.demo.performancequery.security.JwtUtils;
import com.demo.performancequery.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        User user = userService.findUserByEmail(loginRequest.getUsername());
        String token = jwtUtils.generateToken(user);
        return ResponseEntity.ok(token);
    }
}
