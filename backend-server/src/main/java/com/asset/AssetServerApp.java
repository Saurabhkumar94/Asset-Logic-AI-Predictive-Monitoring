package com.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssetServerApp {
    public static void main(String[] args) {
        SpringApplication.run(AssetServerApp.class, args);
        System.out.println("✅ Asset Logic AI Backend Server start ho gaya hai!");
    }
}