package com.example.springcinemawebapp.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {

    private Long id;
    private String title;
    private LocalTime length;
    private String description;

}
