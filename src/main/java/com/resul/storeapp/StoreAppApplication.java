package com.resul.storeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StoreAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreAppApplication.class, args);
    }

}
