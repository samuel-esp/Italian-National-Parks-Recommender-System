package com.example.sii_2021.services;

import com.example.sii_2021.entities.Trail;
import com.example.sii_2021.repositories.TrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrailService {

    @Autowired
    private TrailRepository trailRepository;

    public Trail saveTrail(Trail t){
        return trailRepository.save(t);
    }


}
