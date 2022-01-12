package com.example.sii_2021.services;

import com.example.sii_2021.entities.User;
import com.example.sii_2021.repositories.UserRepository;
import com.example.sii_2021.repositories.projections.UserLinkOnlyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getByUserLink(String userLink){
        return userRepository.findByUserLink(userLink);
    }

    public void saveUserSet(Set<User> userSet){
        userRepository.saveAll(userSet);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Set<UserLinkOnlyDTO> getAllUsersLinkSet(){
        Set<UserLinkOnlyDTO> usersLink = userRepository.findBy();
        return usersLink;
        /*
        List<User> userList = userRepository.findAll();
        Set<String> userLinkSet = new HashSet<>();
        for(User u: userList){
            userLinkSet.add(u.getUserLink());
        }*/
    }
}
