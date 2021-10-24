package com.example.springcinemawebapp.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_row_number", nullable = false)
    private Integer rowNumber;

    @Column(nullable = false)
    private Integer seatNumber;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private MovieSession session;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
}
