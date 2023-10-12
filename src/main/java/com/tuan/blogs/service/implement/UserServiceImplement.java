package com.tuan.blogs.service.implement;

import com.tuan.blogs.dto.BearerToken;
import com.tuan.blogs.dto.LoginDto;
import com.tuan.blogs.dto.RegisterDto;
import com.tuan.blogs.jwt.JwtTokenProvider;
import com.tuan.blogs.model.role.Role;
import com.tuan.blogs.model.role.RoleName;
import com.tuan.blogs.model.user.User;
import com.tuan.blogs.repository.RoleRepository;
import com.tuan.blogs.repository.UserRepository;
import com.tuan.blogs.security.CustomUserDetail;
import com.tuan.blogs.security.CustomUserDetailService;
import com.tuan.blogs.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String authenticate(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        CustomUserDetail customUserDetail = CustomUserDetail.mapUserToUserDetail(user);
        return jwtTokenProvider.generateToken(customUserDetail);
    }

    @Override
    public ResponseEntity<?> register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already in System! ", HttpStatus.SEE_OTHER);
        } else {
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            Role role = roleRepository.findByName(RoleName.ROLE_USER.name());
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            CustomUserDetail customUserDetails = CustomUserDetail.mapUserToUserDetail(user);
            String token = jwtTokenProvider.generateToken(customUserDetails);
            return new ResponseEntity<>(new BearerToken(token, "Bearer "), HttpStatus.OK);
        }
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
