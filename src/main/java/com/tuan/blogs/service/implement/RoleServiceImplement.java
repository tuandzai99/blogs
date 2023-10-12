package com.tuan.blogs.service.implement;


import com.tuan.blogs.model.role.Role;
import com.tuan.blogs.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class RoleServiceImplement implements RoleService {

    @Override
    public Role saveRole(Role role) {
        return null;
    }
}
