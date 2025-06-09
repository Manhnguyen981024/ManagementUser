package com.demo.performancequery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PerformanceQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceQueryApplication.class, args);
    }

}
