package com.demo.performancequery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
//@EnableScheduling
public class PerformanceQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceQueryApplication.class, args);
    }

}
