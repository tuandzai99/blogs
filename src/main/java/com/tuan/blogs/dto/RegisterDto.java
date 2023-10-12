package com.tuan.blogs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterDto implements Serializable {
    private String username;
    private String password;
    private String email;
}
