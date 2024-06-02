package com.example.javalab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JavaLab2Application {

    public static void main(String[] args) {
        SpringApplication.run(JavaLab2Application.class, args);
    }
}
