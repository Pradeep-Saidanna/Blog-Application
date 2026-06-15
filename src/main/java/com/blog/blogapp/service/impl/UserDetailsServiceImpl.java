package com.blog.blogapp.service.impl;

import com.blog.blogapp.entity.User;
import com.blog.blogapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        System.out.println("LOGIN ATTEMPT: " + email);

        User user = userRepository.findByEmail(email);
        System.out.println("DB Password = " + user.getPassword());
        System.out.println("Role = " + user.getRole());

        if(user == null) {
            throw new UsernameNotFoundException(
                    "User not found"
            );
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(
                                user.getRole()
                        )
                )
        );
    }
}