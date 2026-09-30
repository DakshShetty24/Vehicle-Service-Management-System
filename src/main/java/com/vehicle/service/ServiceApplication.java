package com.vehicle.service;

import com.vehicle.service.entity.User;
import com.vehicle.service.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepo) {

        return args -> {

            if (userRepo.findByEmail("admin@vsms.com").isEmpty()) {

                User admin = new User();

                admin.setName("System Administrator");
                admin.setEmail("admin@vsms.com");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");

                userRepo.save(admin);

                System.out.println("=================================");
                System.out.println("Default Admin Created");
                System.out.println("Email: admin@vsms.com");
                System.out.println("Password: admin123");
                System.out.println("=================================");

            } else {

                System.out.println("Admin account already exists.");
            }
        };
    }
}