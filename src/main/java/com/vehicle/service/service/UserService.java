package com.vehicle.service.service;

import com.vehicle.service.entity.User;
import com.vehicle.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public boolean register(User user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }

        user.setRole("CUSTOMER");

        userRepo.save(user);

        return true;
    }

    public User login(String email, String password) {

        return userRepo.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }
}