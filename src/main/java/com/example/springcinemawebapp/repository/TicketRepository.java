package com.example.springcinemawebapp.repository;

import com.example.springcinemawebapp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllBySession_IdEquals(Long id);
}
