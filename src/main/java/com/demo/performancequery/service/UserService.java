package com.demo.performancequery.service;

import com.demo.performancequery.dto.RoleDTO;
import com.demo.performancequery.dto.UserDTO;
import com.demo.performancequery.entity.Role;
import com.demo.performancequery.entity.User;
import com.demo.performancequery.repository.RoleRepository;
import com.demo.performancequery.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    private BatchService batchService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private final int TOTAL = 1_000_000;
    private final int THREADS = 10;
    private final int BATCH_SIZE = TOTAL / THREADS;

    public Page<User> getUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail()))
                .orElse(null);
    }

    @Cacheable(value = "userRoles", key = "#userId")
    public Set<RoleDTO> getRolesByUserId(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    Set<Role> cloned = new HashSet<>(user.getRoles()); // clone để tránh bị concurrent
                    return cloned.stream()
                            .map(r -> new RoleDTO(r.getId(), r.getName()))
                            .collect(Collectors.toSet());
                })
                .orElse(Collections.emptySet());
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public Page<User> getUsersByName(String name, int page, int size, String sortBy) {
        return userRepository.findUserByNameContaining(name, PageRequest.of(page, size, Sort.by(sortBy)));
    }
//    @Override
//    public void run(String... args) throws Exception {
//        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
//        CountDownLatch latch = new CountDownLatch(THREADS);
//
//        System.out.print("Chay ne");
//        for (int i = 0; i < THREADS; i++) {
//            final int start = i * BATCH_SIZE + 1;
//            final int end = (i + 1) * BATCH_SIZE;
//
//            executor.submit(() -> {
//                try {
//                    insertBatch(start, end);
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//
//        latch.await(); // Chờ tất cả thread hoàn thành
//        executor.shutdown();
//        System.out.println("✅ DONE inserting " + TOTAL + " records!");
//    }

    private void insertBatch(int start, int end) {
        List<User> buffer = new ArrayList<>(1000);

        for (int i = start; i <= end; i++) {
            User user = new User();
            user.setName("User " + i);
            user.setEmail("user" + i + "@example.com");
            buffer.add(user);

            if (buffer.size() >= 1000) {
                batchService.saveBatch(buffer);
                buffer.clear();
            }
        }

        if (!buffer.isEmpty()) {
            batchService.saveBatch(buffer);
        }

        System.out.println("Inserted from " + start + " to " + end);
    }
}
