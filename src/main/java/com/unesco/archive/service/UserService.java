package com.unesco.archive.service;

import com.unesco.archive.model.User;
import com.unesco.archive.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }
    public User updateUser(User user){
        return userRepo.save(user);
    }
    public User getUserByUserName(String username){
        return userRepo.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }
}
