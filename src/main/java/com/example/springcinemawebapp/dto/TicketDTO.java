package com.example.springcinemawebapp.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import static com.example.springcinemawebapp.properties.TechnicalConstants.NUMBER_OF_ROWS;
import static com.example.springcinemawebapp.properties.TechnicalConstants.NUMBER_OF_SEATS_PER_ROW;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TicketDTO {
    @Max(value = NUMBER_OF_ROWS, message = "{message.wrong.maximum}")
    private Integer rowNumber;

    @Max(value = NUMBER_OF_SEATS_PER_ROW, message = "{message.wrong.maximum}")
    private Integer seatNumber;
}
