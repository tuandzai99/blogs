package com.tuan.blogs.security;

import com.tuan.blogs.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean userStatus;
    private Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetail mapUserToUserDetail(User user) {
        List<GrantedAuthority> grantedAuthoritys = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new CustomUserDetail(user.getId(),
                user.getUsername(), user.getPassword(), user.getEmail(),
                user.getPhone(), user.getUserstatus(), grantedAuthoritys);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
