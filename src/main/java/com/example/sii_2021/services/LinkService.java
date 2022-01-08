package com.example.sii_2021.services;

import com.example.sii_2021.entities.Link;
import com.example.sii_2021.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Transactional
    public Set<Link> getLinksByStatus(String status){
        return linkRepository.findByStatus(status);
    }

    @Transactional
    public Link saveEntity(Link link){
         return linkRepository.save(link);
    }


}
