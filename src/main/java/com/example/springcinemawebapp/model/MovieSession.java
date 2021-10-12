package com.example.springcinemawebapp.model;

import java.time.LocalTime;
import java.util.List;

public class MovieSession {

    private Long id;
    private LocalTime start;
    private LocalTime end;

    private Movie movie;

    private List<Ticket> tickets;
}
