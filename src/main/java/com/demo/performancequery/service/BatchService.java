package com.demo.performancequery.service;

import com.demo.performancequery.entity.User;
import com.demo.performancequery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BatchService {

    private UserRepository userRepository;

    @Transactional
    public void saveBatch(List<User> users) {
        userRepository.saveAll(users);
    }
}
