package com.example.sii_2021.repositories;

import com.example.sii_2021.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface LinkRepository extends JpaRepository<Link, String> {

    public Set<Link> findByStatus(String status);


}
