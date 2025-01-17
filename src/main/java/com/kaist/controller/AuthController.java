package com.kaist.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaist.entity.User;
import com.kaist.security.JwtFilter;
import com.kaist.service.AuthService;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("authenticate")
    public ResponseEntity authorize( @RequestBody User user) {
        String token = authService.login(user);
        HashMap<String, Object> result = new HashMap<>();
        result.put("token",token);
        result.put("status",HttpStatus.OK);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }
}