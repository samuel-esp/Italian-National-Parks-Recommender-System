package com.example.sii_2021.repositories;

import com.example.sii_2021.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserLink(String userLink);


}