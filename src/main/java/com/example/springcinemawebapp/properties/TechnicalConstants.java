package com.example.springcinemawebapp.properties;

import java.time.LocalTime;

public interface TechnicalConstants {
    int PAGE_SIZE = 10;
    LocalTime MOVIE_SESSIONS_START_TIME = LocalTime.of(9, 0);
    LocalTime MOVIE_SESSIONS_END_TIME = LocalTime.of(22, 0);

    int NUMBER_OF_ROWS = 8;
    int NUMBER_OF_SEATS_PER_ROW = 10;
    int TICKET_PRICE = 10;

    int BASE64_ENCRYPT_ROUNDS = 12;
}
