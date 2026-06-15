package com.blog.blogapp.service;

import com.blog.blogapp.dto.RegisterDto;
import com.blog.blogapp.entity.User;

public interface UserService {

    User saveUser(RegisterDto registerDto);

    User findByEmail(String email);
}