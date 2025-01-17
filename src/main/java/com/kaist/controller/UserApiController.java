package com.kaist.controller;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaist.dto.UserDTO;
import com.kaist.entity.User;
import com.kaist.repository.UserRepository;
import com.kaist.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    
    private final UserRepository userRepo;
    
    

    @PostMapping("signup")
    public User signup(@RequestBody User user) {
        return userService.signup(user);
    }
    
    @PostMapping("/admin/signup")
    public User adminSignup(@RequestBody User user) {
        return userService.adminSignup(user);
    }

//    @GetMapping("users/info")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//    public User getMyInfo() {
//        return userService.findUserInfo();
//    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Optional<User> getUserInfo(@PathVariable Long userId) {
        return userService.findById(userId);
    }
    
    @GetMapping("/users/all/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Optional<User> getUserInfoAll(@PathVariable Long userId) {
        return userService.findById(userId);
    }
    
    @GetMapping("/users/admin/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Optional<User> getUserInfoAdmin(@PathVariable Long userId) {
        return userService.findById(userId);
    }
    
    @GetMapping("/users/all/dto/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public UserDTO getUserInfoAllDto(@PathVariable String userId) {
        return userService.findByEmail(userId);
    }
    
    
}