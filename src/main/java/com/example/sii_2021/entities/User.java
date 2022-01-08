package com.example.sii_2021.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "user_id", scope = User.class)
@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String userLink;

    @OneToMany(mappedBy="user")
    private Set<Rating> ratingsSet;

    public User(String userName, String userLink, HashSet<Rating> es) {
        this.name = userName;
        this.userLink = userLink;
        this.ratingsSet = es;
    }
}
