package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDto;
import com.pm.authservice.model.User;
import com.pm.authservice.repo.UserRepo;
import com.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private  final UserService userService;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtUtil jwtUtil;
    public Optional<String>authenticate(LoginRequestDto dto){
        Optional<String>token=userService
                .findByEmail(dto.getEmail())
                .filter(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail(), user.getRole()));
        return token;
    }
    public  boolean validateToken(String token) {
        return jwtUtil.validateToken(token);


    }
}
