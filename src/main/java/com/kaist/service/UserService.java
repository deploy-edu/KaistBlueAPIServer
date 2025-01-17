package com.kaist.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaist.dto.UserDTO;
import com.kaist.entity.Role;
import com.kaist.entity.User;
import com.kaist.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(User user) {
        if (userRepository.findByUserId(user.getUserId()) != null) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        User nUser = User.builder()
                .userId(user.getUserId())
                .uPassword(passwordEncoder.encode(user.getUPassword()))
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(nUser);

        return nUser;
    }
    
    @Transactional
    public User adminSignup(User user) {
        if (userRepository.findByUserId(user.getUserId()) != null) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        User nUser = User.builder()
                .userId(user.getUserId())
                .uPassword(passwordEncoder.encode(user.getUPassword()))
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(Role.ROLE_ADMIN)
                .build();

        userRepository.save(nUser);

        return nUser;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public UserDTO findByEmail(String email) {
    	User user = userRepository.findByEmail(email);
    	UserDTO userDTO = new UserDTO();
    	
    	userDTO.setEmail(user.getEmail());
    	userDTO.setId(user.getId());
    	userDTO.setUserName(user.getUserName());
    	userDTO.setUserId(user.getUserId());
    	
        return userDTO;
    }
}