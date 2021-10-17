package com.example.springcinemawebapp.dto;

import lombok.*;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;

import javax.validation.constraints.*;
import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MovieDTO {

    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Size(min = 2, max = 30, message = "{message.wrong.size.title}")
    private String title;

    @NotNull(message = "{message.wrong.value.null}")
    @DurationMin(minutes = 10, message = "{message.wrong.movie.duration.min}")
    @DurationMax(hours = 10, message = "{message.wrong.movie.duration.max}")
    private Duration duration;

    private String description;

}
