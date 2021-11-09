package com.example.springcinemawebapp.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public LocalTime getEnd() {
        return start.plus(movie.getDuration());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieSession session = (MovieSession) o;
        return Objects.equals(date, session.date) && Objects.equals(start, session.start) && Objects.equals(movie, session.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, start, movie);
    }
}
