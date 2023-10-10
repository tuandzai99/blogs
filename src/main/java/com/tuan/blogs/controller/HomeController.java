package com.tuan.blogs.controller;


import com.tuan.blogs.model.user.Address;
import com.tuan.blogs.model.user.User;
import com.tuan.blogs.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("blogs")
public class HomeController {

    @Autowired
    private AddressService addressService;

    @GetMapping("")
    public ResponseEntity<?> getHome() {
        User user = new User();
        return ResponseEntity.ok("HOME");
    }
}
