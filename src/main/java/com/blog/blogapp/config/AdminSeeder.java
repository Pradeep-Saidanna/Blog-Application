package com.blog.blogapp.config;

import com.blog.blogapp.entity.User;
import com.blog.blogapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AdminSeeder(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("xxxxxxxxxxxxx@gmail.com");
        admin.setPassword(encoder.encode("xxxxxxxxxxxx"));
        admin.setRole("ROLE_ADMIN");

        repo.save(admin);

        System.out.println("Admin inserted");
    }
}