package com.example.springcinemawebapp.model;


import lombok.*;

import javax.persistence.*;
import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Duration duration;

    @Column
    private String description;

}
