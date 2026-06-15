package com.blog.blogapp.config;

import com.blog.blogapp.entity.User;
import com.blog.blogapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Scanner;

@Configuration
public class AdminSeeder {

    @Bean
    public CommandLineRunner init(UserRepository repo,
                                  PasswordEncoder encoder) {
        Scanner sc = new Scanner(System.in);

        return args -> {


            User existing = repo.findByEmail("xxxxxxxxxxxxx@gmail.com");

            if (existing == null) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("xxxxxxxxxxxxx@gmail.com");
                admin.setPassword(encoder.encode("xxxxxxxxxxxx"));
                admin.setRole("ROLE_ADMIN");

                repo.save(admin);

                System.out.println("Admin inserted");
            } else {
                System.out.println("Admin already exists");
            }
        };
    }
}