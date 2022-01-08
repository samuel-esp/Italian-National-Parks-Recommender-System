package com.example.sii_2021.services;

import com.example.sii_2021.entities.User;
import com.example.sii_2021.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getByUserLink(String userLink){
        return userRepository.findByUserLink(userLink);
    }

    public void saveUserSet(List<User> userSet){
        for(User user: userSet) {
            userRepository.save(user);
        }
    }

}
