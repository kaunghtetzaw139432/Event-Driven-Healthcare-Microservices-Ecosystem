package com.pm.authservice.service;

import com.pm.authservice.model.User;
import com.pm.authservice.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private  final UserRepo userRepo;
    public Optional<User>findByEmail(String email){
        return userRepo.findByEmail(email);
    }
}
