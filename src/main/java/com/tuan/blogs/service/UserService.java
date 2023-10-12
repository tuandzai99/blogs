package com.tuan.blogs.service;

import com.tuan.blogs.dto.LoginDto;
import com.tuan.blogs.dto.RegisterDto;
import com.tuan.blogs.model.role.Role;
import com.tuan.blogs.model.user.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    String authenticate(LoginDto loginDto);

    ResponseEntity<?> register(RegisterDto registerDto);

    User saveUser(User user);
}

