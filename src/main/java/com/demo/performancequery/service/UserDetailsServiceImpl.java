package com.demo.performancequery.service;

import com.demo.performancequery.dto.RoleAuthority;
import com.demo.performancequery.entity.User;
import com.demo.performancequery.repository.RoleRepository;
import com.demo.performancequery.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;

    public UserDetailsServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, @Lazy  CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
    }

    @Override
    @Cacheable(cacheNames = "userDetails", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Cache cache = cacheManager.getCache("userDetails");
//        UserDetails cached = cache.get(username, UserDetails.class);
//        if (cached != null)
//            return cached;

        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        UserDetails userDetails = new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                passwordEncoder.encode("admin123"),
                user.getRoles().stream()
                        .map(r -> new RoleAuthority(r.getName()))
                        .collect(Collectors.toList())
        );
//        cache.put(username, userDetails);
        return userDetails;
    }
}
