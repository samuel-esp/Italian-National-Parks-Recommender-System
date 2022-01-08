package com.example.sii_2021.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "trail_id", scope = Trail.class)
@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @Builder
@Table(name = "trails")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Trail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trail_id")
    @EqualsAndHashCode.Include
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="trail")
    @ToString.Exclude
    private Set<Rating> ratingsSet;

    //extract trail info
    private String name;
    private Double lengthKm;
    private Double elevationM;

    //extract route type
    private Integer loop;
    private Integer outAndBack;
    private Integer pointToPoint;

    //extract trail difficulties
    private Integer easy;
    private Integer medium;
    private Integer hard;

    //extract activity tags
    private Integer backpacking;
    private Integer bikeTouring;
    private Integer birdWatching;
    private Integer camping;
    private Integer crossCountrySkiing;
    private Integer fishing;
    private Integer hiking;
    private Integer horseBackRiding;
    private Integer mountainBiking;
    private Integer natureTrips;
    private Integer ohvOffRoadDriving;
    private Integer paddleSports;
    private Integer roadBiking;
    private Integer rockClimbing;
    private Integer scenicDriving;
    private Integer skiing;
    private Integer snowshoeing;
    private Integer running;
    private Integer viaFerrata;
    private Integer walking;

    //extract attractions tag
    private Integer beach ;
    private Integer cave;
    private Integer cityWalk;
    private Integer forest;
    private Integer event;
    private Integer historicSite;
    private Integer hotSprings;
    private Integer lake;
    private Integer pubWalk;
    private Integer railsTrails;
    private Integer river;
    private Integer views;
    private Integer wildflowers;
    private Integer wildlife;
    private Integer waterfalls;

    //extract suitability tag
    private Integer dogFriendly;
    private Integer paved;
    private Integer kidFriendly;
    private Integer partiallyPaved;
    private Integer wheelchairFriendly;
    private Integer strollerFriendly;

}
