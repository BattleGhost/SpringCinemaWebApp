package com.example.springcinemawebapp.repository;

import com.example.springcinemawebapp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
