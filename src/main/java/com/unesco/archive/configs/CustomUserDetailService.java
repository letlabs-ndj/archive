package com.unesco.archive.configs;

import com.unesco.archive.model.User;
import com.unesco.archive.model.enums.UserRole;
import com.unesco.archive.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()){
            var userObj = user.get();
            System.out.println(userObj);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        }else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(User userObj) {
        if (userObj.getRole().isEmpty()){
            return new String[]{"ADMIN"};
        }
        return userObj.getRole().split(",");
    }
}
