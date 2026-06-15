package com.blog.blogapp.service.impl;

import com.blog.blogapp.dto.RegisterDto;
import com.blog.blogapp.entity.User;
import com.blog.blogapp.repository.UserRepository;
import com.blog.blogapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(RegisterDto registerDto) {

        User user = new User();

        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        registerDto.getPassword()
                )
        );

        user.setRole("ROLE_AUTHOR");

        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}