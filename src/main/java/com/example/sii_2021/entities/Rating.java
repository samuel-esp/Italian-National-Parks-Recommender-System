package com.example.sii_2021.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "rating_id", scope = Rating.class)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString @Builder
@Table(name = "ratings")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rating_id")
    private Long id;

    private Double userRating;

    @ManyToOne
    @JoinColumn(name="trail_id", nullable=false)
    @EqualsAndHashCode.Include
    private Trail trail;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @EqualsAndHashCode.Include
    private User user;

}
