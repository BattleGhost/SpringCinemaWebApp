package com.example.springcinemawebapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_sessions")
@Builder
public class MovieSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime start;

    @Column(name = "end")
    @Access(AccessType.PROPERTY)
    private LocalTime end;

    @ManyToOne
    private Movie movie;

    @OneToMany(mappedBy = "session")
    private List<Ticket> tickets;

    public LocalTime getEnd() {
        return start.plus(movie.getDuration());
    }
}
