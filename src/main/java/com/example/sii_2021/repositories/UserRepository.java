package com.example.sii_2021.repositories;

import com.example.sii_2021.entities.User;
import com.example.sii_2021.repositories.projections.UserLinkOnlyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserLink(String userLink);

    Set<UserLinkOnlyDTO> findBy();

}