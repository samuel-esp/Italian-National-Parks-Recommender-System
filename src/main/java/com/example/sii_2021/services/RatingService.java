package com.example.sii_2021.services;

import com.example.sii_2021.entities.Rating;
import com.example.sii_2021.repositories.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RatingService {

    @Autowired
    private RatingsRepository ratingsRepository;

    public void saveRatings(Set<Rating> ratingSet){
        ratingsRepository.saveAll(ratingSet);
    }

    public void saveRating(Rating rating){
        ratingsRepository.save(rating);
    }


}
